package com.moxa.dream.system.mapper.factory;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.system.annotation.Mapper;
import com.moxa.dream.system.annotation.Param;
import com.moxa.dream.system.annotation.Result;
import com.moxa.dream.system.annotation.Sql;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapper.Action;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.system.mapper.handler.MapperHandler;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.BaseReflectHandler;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.reflection.util.NonCollection;
import com.moxa.dream.util.reflection.util.NullObject;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.*;


public abstract class AbstractMapperFactory implements MapperFactory {
    protected Map<Method, MethodInfo> methodInfoMap = new HashMap<>();
    protected Map<Class, Class[]> mapperTypeMap = new HashMap<>();

    @Override
    public void addMapper(Configuration configuration, Class mapperClass) {
        if (isMapper(mapperClass)) {
            if (mapperTypeMap.containsKey(mapperClass)) {
                return;
            }
            Map<String, MethodInfo.Builder> builderMap = new HashMap<>();
            List<Method> methodList = ReflectUtil.findMethod(mapperClass);
            if (!ObjectUtil.isNull(methodList)) {
                for (Method method : methodList) {
                    if (!method.isDefault()) {
                        String name = method.getName();
                        if (builderMap.containsKey(name)) {
                            throw new DreamRunTimeException("方法名'" + name + "'重复定义");
                        }
                        MethodInfo.Builder builder = createMethodInfoBuilder(configuration, mapperClass, method);
                        builderMap.put(name, builder);
                    }
                }
            }
            fillMethodInfoFromResource(configuration, mapperClass, builderMap);
            for (String name : builderMap.keySet()) {
                MethodInfo.Builder builder = builderMap.get(name);
                MethodInfo methodInfo = builder.build();
                if (methodInfo != null) {
                    methodInfoMap.put(methodInfo.getMethod(), methodInfo);
                }
            }
            this.mapperTypeMap.put(mapperClass, getAllInterface(mapperClass));
        }
    }

    protected abstract void fillMethodInfoFromResource(Configuration configuration, Class type, Map<String, MethodInfo.Builder> builderMap);

    protected MethodInfo.Builder createMethodInfoBuilder(Configuration configuration, Class mapperClass, Method method) {
        Class<? extends Collection> rowType = getRowType(mapperClass, method);
        Class colType = getColType(mapperClass, method);
        String[] columnNames = getColumnNames(mapperClass, method);
        boolean cache = isCache(mapperClass, method);
        Command command = getCommand(mapperClass, method);
        String[] paramNameList = getParamNameList(method);
        String sql = getSql(method);
        Integer timeOut = getTimeOut(method);
        Action[] initActionList = getInitActionList(method);
        Action[] loopActionList = getLoopActionList(method);
        Action[] destroyActionList = getDestroyActionList(method);
        MethodInfo.Builder builder = new MethodInfo.Builder(configuration)
                .name(method.getName())
                .rowType(rowType)
                .colType(colType)
                .columnNames(columnNames)
                .cache(cache)
                .command(command)
                .paramNameList(paramNameList)
                .sql(sql)
                .timeOut(timeOut)
                .initActionList(initActionList)
                .loopActionList(loopActionList)
                .destroyActionList(destroyActionList)
                .method(method);
        return builder;
    }

    protected Action[] getInitActionList(Method method) {
        return null;
    }

    protected Action[] getLoopActionList(Method method) {
        return null;
    }

    protected Action[] getDestroyActionList(Method method) {
        return null;
    }

    protected boolean isMapper(Class mapperClass) {
        return mapperClass.isAnnotationPresent(Mapper.class);
    }

    protected String getSql(Method method) {
        Sql sqlAnnotation = method.getDeclaredAnnotation(Sql.class);
        if (sqlAnnotation == null)
            return null;
        else
            return sqlAnnotation.value();
    }

    protected Integer getTimeOut(Method method) {
        Sql sqlAnnotation = method.getDeclaredAnnotation(Sql.class);
        if (sqlAnnotation == null)
            return null;
        String timeOut = sqlAnnotation.timeOut();
        if (ObjectUtil.isNull(timeOut))
            return null;
        return Integer.valueOf(timeOut);
    }

    protected String getParamName(Parameter parameter) {
        Param paramAnnotation = parameter.getDeclaredAnnotation(Param.class);
        if (paramAnnotation != null)
            return paramAnnotation.value();
        else
            return null;
    }

    protected Class<? extends Collection> getRowType(Class mapperClass, Method method) {
        Result resultAnnotation = method.getDeclaredAnnotation(Result.class);
        Class<? extends Collection> rowType;
        if (resultAnnotation == null) {
            rowType = ReflectUtil.getRowType(mapperClass, method);
        } else {
            rowType = resultAnnotation.rowType();
            if (rowType == NullObject.class) {
                rowType = ReflectUtil.getRowType(mapperClass, method);
            }
        }
        if (rowType == null)
            rowType = NonCollection.class;
        return rowType;
    }

    protected Class getColType(Class mapperClass, Method method) {
        Result resultAnnotation = method.getDeclaredAnnotation(Result.class);
        Class<?> colType;
        if (resultAnnotation == null) {
            colType = ReflectUtil.getColType(mapperClass, method);
        } else {
            colType = resultAnnotation.colType();
            if (colType == NullObject.class) {
                colType = ReflectUtil.getColType(mapperClass, method);
            }
        }
        return colType;
    }

    protected String[] getColumnNames(Class mapperClass, Method method) {
        Result resultAnnotation = method.getDeclaredAnnotation(Result.class);
        if (resultAnnotation != null) {
            String[] columnNames = resultAnnotation.columnNames();
            if (!ObjectUtil.isNull(columnNames)) {
                return columnNames;
            }
        }
        return new String[0];
    }

    protected boolean isCache(Class mapperClass, Method method) {
        Result resultAnnotation = method.getDeclaredAnnotation(Result.class);
        boolean cache = true;
        if (resultAnnotation != null) {
            cache = resultAnnotation.cache();
        }
        return cache;
    }
    protected Command getCommand(Class mapperClass, Method method) {
        Result resultAnnotation = method.getDeclaredAnnotation(Result.class);
        Command command=Command.NONE;
        if (resultAnnotation != null) {
            command=resultAnnotation.command();
        }
        return command;
    }
    protected String[] getParamNameList(Method method) {
        Parameter[] parameters = method.getParameters();
        if (!ObjectUtil.isNull(parameters)) {
            String[] paramList = new String[parameters.length];
            if (parameters.length > 1) {
                for (int i = 0; i < parameters.length; i++) {
                    Parameter parameter = parameters[i];
                    String paramName = getParamName(parameter);
                    if (paramName == null)
                        paramName = parameter.getName();
                    paramList[i] = paramName;
                }
            } else {
                paramList[0] = getParamName(parameters[0]);
            }
            return paramList;
        }
        return null;
    }

    protected Class[] getAllInterface(Class type) {
        return ReflectUtil.find(type, new BaseReflectHandler<Class>() {
            @Override
            public List<Class> doHandler(Class type) {
                if (type.isInterface()) {
                    return Arrays.asList(type);
                } else
                    return null;
            }
        }).toArray(new Class[0]);
    }

    @Override
    public <T> T getMapper(Class<T> type, MapperHandler mapperHandler) {
        Class[] typeList = mapperTypeMap.get(type);
        ObjectUtil.requireNonNull(typeList, "类 '" + type.getName() + "'未在Mapper注册");
        return (T) Proxy.newProxyInstance(type.getClassLoader(), typeList, (proxy, method, args) -> {
            MethodInfo methodInfo = methodInfoMap.get(method);
            if (methodInfo != null) {
                Object arg = null;
                if (!ObjectUtil.isNull(args)) {
                    if (args.length == 1) {
                        String paramName = methodInfo.getParamNameList()[0];
                        if (ObjectUtil.isNull(paramName)) {
                            arg = args[0];
                        } else {
                            Map<String, Object> paramMap = new HashMap<>();
                            paramMap.put(paramName, args[0]);
                            arg = paramMap;
                        }
                    } else {
                        String[] paramNameList = methodInfo.getParamNameList();
                        Map<String, Object> paramMap = new HashMap<>();
                        for (int i = 0; i < paramNameList.length; i++) {
                            paramMap.put(paramNameList[i], args[i]);
                        }
                        arg = paramMap;
                    }
                }
                return mapperHandler.handler(methodInfo, arg);
            } else {
                return handler(type, proxy, method, args);
            }
        });
    }

    protected Object handler(Class type, Object proxy, Method method, Object[] args) throws Throwable {
        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                .getDeclaredConstructor(Class.class);
        constructor.setAccessible(true);
        return constructor.newInstance(type)
                .in(type)
                .unreflectSpecial(method, type)
                .bindTo(proxy)
                .invokeWithArguments();
    }

    @Override
    public Collection<Class> getMapperTypeList() {
        return mapperTypeMap.keySet();
    }
}
