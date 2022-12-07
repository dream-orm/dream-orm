package com.moxa.dream.system.mapper;

import com.moxa.dream.system.annotation.Mapper;
import com.moxa.dream.system.annotation.Param;
import com.moxa.dream.system.annotation.Setup;
import com.moxa.dream.system.annotation.Sql;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.config.MethodParam;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.provider.ActionProvider;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.common.NullObject;
import com.moxa.dream.util.common.ObjectMap;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;


public class DefaultMapperFactory implements MapperFactory {
    protected Map<Method, MethodInfo> methodInfoMap = new HashMap<>();
    protected Map<Class, Class[]> mapperTypeMap = new HashMap<>();

    @Override
    public boolean addMapper(Configuration configuration, Class mapperClass) {
        if (isMapper(mapperClass)) {
            Map<String, MethodInfo.Builder> builderMap = new HashMap<>();
            List<Method> methodList = ReflectUtil.findMethod(mapperClass);
            if (!ObjectUtil.isNull(methodList)) {
                for (Method method : methodList) {
                    String name = method.getName();
                    if (builderMap.containsKey(name)) {
                        throw new DreamRunTimeException("方法名'" + name + "'重复定义，请检查" + mapperClass.getName());
                    }
                    MethodInfo.Builder builder = createMethodInfoBuilder(configuration, mapperClass, method);
                    builderMap.put(name, builder);
                }
            }
            fillMethodInfoFromAction(configuration, mapperClass, builderMap);
            for (String name : builderMap.keySet()) {
                MethodInfo.Builder builder = builderMap.get(name);
                MethodInfo methodInfo = builder.build();
                if (methodInfo != null) {
                    methodInfoMap.put(methodInfo.getMethod(), methodInfo);
                }
            }
            this.mapperTypeMap.put(mapperClass, getAllInterface(mapperClass));
            return true;
        } else {
            return false;
        }
    }

    protected Class<?> getActionType(Class<?> type) {
        Mapper mapperAnnotation = type.getAnnotation(Mapper.class);
        if (mapperAnnotation == null) {
            return null;
        }
        Class<?> value = mapperAnnotation.value();
        if (value == NullObject.class) {
            return null;
        }
        return value;
    }

    protected void fillMethodInfoFromAction(Configuration configuration, Class type, Map<String, MethodInfo.Builder> builderMap) {
        Class<?> actionType = getActionType(type);
        List<Method> methodList = ReflectUtil.find(actionType,
                (aType) -> Arrays.stream(aType.getDeclaredMethods())
                        .filter(method -> method.getParameters().length == 0)
                        .collect(Collectors.toList()));
        if (!ObjectUtil.isNull(methodList)) {
            Object actionObject = ReflectUtil.create(actionType);
            for (Method method : methodList) {
                String methodName = method.getName();
                MethodInfo.Builder builder = builderMap.get(methodName);
                if (builder == null) {
                    throw new DreamRunTimeException("类" + type.getName() + "不存在方法" + methodName);
                }
                ActionProvider actionProvider = null;
                try {
                    Class<?> returnType = method.getReturnType();
                    Object value = method.invoke(actionObject);
                    if (ActionProvider.class.isAssignableFrom(returnType)) {
                        actionProvider = (ActionProvider) value;
                    } else if (String.class == returnType) {
                        actionProvider = (mapperType) -> (String) value;
                    }
                } catch (Exception e) {
                    throw new DreamRunTimeException("调用方法" + actionType.getName() + "." + methodName + "失败，" + e.getMessage(), e);
                }
                if (actionProvider != null) {
                    Action[] initActionList = actionProvider.init(type);
                    String sql = actionProvider.sql(type);
                    Action[] loopActionList = actionProvider.loop(type);
                    Action[] destroyActionList = actionProvider.destroy(type);
                    Class<? extends Collection> rowType = actionProvider.rowType(type);
                    Class<?> colType = actionProvider.colType(type);
                    Boolean cache = actionProvider.cache(type);
                    Integer timeOut = actionProvider.timeOut(type);
                    if (initActionList != null) {
                        builder.initActionList(initActionList);
                    }
                    if (sql != null) {
                        builder.sql(sql);
                    }
                    if (loopActionList != null) {
                        builder.loopActionList(loopActionList);
                    }
                    if (destroyActionList != null) {
                        builder.destroyActionList(destroyActionList);
                    }
                    if (rowType != null) {
                        builder.rowType(rowType);
                    }
                    if (colType != null) {
                        builder.colType(colType);
                    }
                    if (cache != null) {
                        builder.cache(cache);
                    }
                    if (timeOut != null) {
                        builder.timeOut(timeOut);
                    }
                }
            }
        }
    }

    protected MethodInfo.Builder createMethodInfoBuilder(Configuration configuration, Class mapperClass, Method method) {
        Class<? extends Collection> rowType = getRowType(mapperClass, method);
        Class colType = getColType(mapperClass, method);
        boolean cache = isCache(mapperClass, method);
        MethodParam[] methodParamList = getMethodParamList(method);
        String sql = getSql(configuration, method);
        int timeOut = getTimeOut(method);
        MethodInfo.Builder builder = new MethodInfo.Builder(configuration)
                .name(method.getName())
                .rowType(rowType)
                .colType(colType)
                .cache(cache)
                .methodParamList(methodParamList)
                .sql(sql)
                .timeOut(timeOut)
                .method(method);
        return builder;
    }

    protected boolean isMapper(Class mapperClass) {
        return mapperClass.isAnnotationPresent(Mapper.class);
    }

    protected String getSql(Configuration configuration, Method method) {
        String sql = null;
        Sql sqlAnnotation = method.getDeclaredAnnotation(Sql.class);
        if (sqlAnnotation != null) {
            sql = sqlAnnotation.value();
        }
        return sql;
    }

    protected int getTimeOut(Method method) {
        int timeOut = 0;
        Setup setupAnnotation = method.getDeclaredAnnotation(Setup.class);
        if (setupAnnotation != null) {
            timeOut = setupAnnotation.timeOut();
        }
        return timeOut;
    }

    protected String getParamName(Parameter parameter) {
        Param paramAnnotation = parameter.getDeclaredAnnotation(Param.class);
        if (paramAnnotation != null)
            return paramAnnotation.value();
        else
            return null;
    }

    protected Class<? extends Collection> getRowType(Class mapperClass, Method method) {
        Setup setupAnnotation = method.getDeclaredAnnotation(Setup.class);
        Class<? extends Collection> rowType;
        if (setupAnnotation == null) {
            rowType = ReflectUtil.getRowType(mapperClass, method);
        } else {
            rowType = setupAnnotation.rowType();
            if (rowType == NullObject.class) {
                rowType = ReflectUtil.getRowType(mapperClass, method);
            }
        }
        if (rowType == null)
            rowType = NonCollection.class;
        return rowType;
    }

    protected Class getColType(Class mapperClass, Method method) {
        Setup setupAnnotation = method.getDeclaredAnnotation(Setup.class);
        Class<?> colType;
        if (setupAnnotation == null) {
            colType = ReflectUtil.getColType(mapperClass, method);
        } else {
            colType = setupAnnotation.colType();
            if (colType == NullObject.class) {
                colType = ReflectUtil.getColType(mapperClass, method);
            }
        }
        return colType;
    }

    protected boolean isCache(Class mapperClass, Method method) {
        Setup setupAnnotation = method.getDeclaredAnnotation(Setup.class);
        boolean cache = true;
        if (setupAnnotation != null) {
            cache = setupAnnotation.cache();
        }
        return cache;
    }

    protected MethodParam[] getMethodParamList(Method method) {
        Parameter[] parameters = method.getParameters();
        MethodParam[] methodParamList = null;
        if (!ObjectUtil.isNull(parameters)) {
            methodParamList = new MethodParam[parameters.length];
            if (parameters.length > 1) {
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    String paramName = getParamName(parameter);
                    if (paramName == null)
                        paramName = parameter.getName();
                    methodParamList[i] = new MethodParam(paramName, parameter.getType());
                }
            } else {
                String name = getParamName(parameters[0]);
                Class<?> type = parameters[0].getType();
                methodParamList[0] = new MethodParam(name, type);
            }
        }
        return methodParamList;
    }

    protected Class[] getAllInterface(Class type) {
        return ReflectUtil.find(type, _type -> {
            if (_type.isInterface()) {
                return Arrays.asList(_type);
            } else {
                return null;
            }
        }).toArray(new Class[0]);
    }

    @Override
    public <T> T getMapper(Class<T> type, MapperInvoke mapperInvoke) {
        Class[] typeList = mapperTypeMap.get(type);
        if (typeList == null) {
            return null;
        }
        return (T) Proxy.newProxyInstance(type.getClassLoader(), typeList, (proxy, method, args) -> {
            MethodInfo methodInfo = methodInfoMap.get(method);
            if (methodInfo != null) {
                if (!methodInfo.isCompile()) {
                    methodInfo.compile();
                }
                Map<String, Object> argMap = getArg(methodInfo, args);
                return mapperInvoke.invoke(methodInfo, argMap, type);
            } else {
                return invoke(type, proxy, method, args);
            }
        });
    }

    protected Object invoke(Class type, Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isDefault()) {
            Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                    .getDeclaredConstructor(Class.class);
            constructor.setAccessible(true);
            return constructor.newInstance(type)
                    .in(type)
                    .unreflectSpecial(method, type)
                    .bindTo(proxy)
                    .invokeWithArguments(args);
        } else {
            throw new DreamRunTimeException("接口方法不支持调用，方法名：" + type.getName() + "." + method.getName());
        }
    }

    protected Map<String, Object> getArg(MethodInfo methodInfo, Object[] args) {
        Map<String, Object> arg = null;
        if (!ObjectUtil.isNull(args)) {
            if (args.length == 1) {
                MethodParam methodParam = methodInfo.getMethodParamList()[0];
                String paramName = methodParam.getParamName();
                if (ObjectUtil.isNull(paramName)) {
                    arg = new ObjectMap(args[0]);
                } else {
                    Map<String, Object> paramMap = new HashMap<>();
                    paramMap.put(paramName, args[0]);
                    arg = paramMap;
                }
            } else {
                MethodParam[] methodParamList = methodInfo.getMethodParamList();
                Map<String, Object> paramMap = new HashMap<>();
                for (int i = 0; i < methodParamList.length; i++) {
                    paramMap.put(methodParamList[i].getParamName(), args[i]);
                }
                arg = paramMap;
            }
        }
        return arg;
    }

    @Override
    public Collection<Class> getMapperTypeList() {
        return mapperTypeMap.keySet();
    }
}
