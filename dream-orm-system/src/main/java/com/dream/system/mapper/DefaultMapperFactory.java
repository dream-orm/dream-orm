package com.dream.system.mapper;

import com.dream.system.action.ActionProvider;
import com.dream.system.annotation.*;
import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.config.MethodParam;
import com.dream.system.core.action.DestroyAction;
import com.dream.system.core.action.InitAction;
import com.dream.system.core.action.LoopAction;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.session.Session;
import com.dream.system.core.statementhandler.StatementHandler;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;
import java.util.*;


public class DefaultMapperFactory implements MapperFactory {
    protected Map<String, MethodInfo> methodInfoMap = new HashMap<>(16);
    protected Map<Class, Class[]> mapperTypeMap = new HashMap<>(8);

    @Override
    public void add(MethodInfo methodInfo) {
        String id = methodInfo.getId();
        if (this.methodInfoMap.containsKey(id)) {
            throw new DreamRunTimeException(id + "已注入到Mapper");
        }
        this.methodInfoMap.put(id, methodInfo);
    }

    @Override
    public MethodInfo get(String id) {
        return methodInfoMap.get(id);
    }

    @Override
    public boolean addMapper(Configuration configuration, Class<?> mapperClass) {
        if (!mapperTypeMap.containsKey(mapperClass) && isMapper(mapperClass)) {
            Map<String, MethodInfo> methodInfoMap = new HashMap<>(4);
            List<Method> methodList = ReflectUtil.findMethod(mapperClass);
            if (!ObjectUtil.isNull(methodList)) {
                for (Method method : methodList) {
                    if (!method.isDefault()) {
                        String name = method.getName();
                        if (methodInfoMap.containsKey(name)) {
                            throw new DreamRunTimeException("方法名'" + name + "'重复定义，请检查" + mapperClass.getName());
                        }
                        MethodInfo methodInfo = createMethodInfo(configuration, mapperClass, method);
                        methodInfoMap.put(name, methodInfo);
                    }
                }
            }
            for (String name : methodInfoMap.keySet()) {
                MethodInfo methodInfo = methodInfoMap.get(name);
                if (ObjectUtil.isNull(methodInfo.getSql())) {
                    throw new DreamRunTimeException(methodInfo.getId() + "未绑定SQL");
                }
                add(methodInfo);
            }
            this.mapperTypeMap.put(mapperClass, getAllInterface(mapperClass));
            return true;
        } else {
            return false;
        }
    }

    protected MethodInfo createMethodInfo(Configuration configuration, Class mapperClass, Method method) {
        ActionProvider actionProvider;
        try {
            actionProvider = actionProvider(mapperClass, method);
        } catch (Exception e) {
            throw new DreamRunTimeException("获取对象" + ActionProvider.class + "失败，" + e.getMessage());
        }
        String sql = getSql(mapperClass, method, actionProvider);
        if (ObjectUtil.isNull(sql)) {
            throw new DreamRunTimeException(method.getDeclaringClass().getName() + "." + method.getName() + "未绑定SQL");
        }
        MethodParam[] methodParamList = getMethodParamList(method);
        Class<? extends Collection> rowType = getRowType(mapperClass, method, actionProvider);
        Class colType = getColType(mapperClass, method, actionProvider);
        int timeOut = getTimeOut(mapperClass, method, actionProvider);
        String page = getPage(mapperClass, method, actionProvider);
        StatementHandler statementHandler = statementHandler(mapperClass, method, actionProvider);
        ResultSetHandler resultSetHandler = resultSetHandler(mapperClass, method, actionProvider);
        InitAction[] initActions = initActions(mapperClass, method, actionProvider);
        LoopAction[] loopActions = loopActions(mapperClass, method, actionProvider);
        DestroyAction[] destroyActions = destroyActions(mapperClass, method, actionProvider);
        return new MethodInfo()
                .setConfiguration(configuration)
                .setId(getId(method))
                .setRowType(rowType)
                .setColType(colType)
                .setMethodParamList(methodParamList)
                .setSql(sql)
                .setTimeOut(timeOut)
                .setPage(page)
                .setMethod(method)
                .setStatementHandler(statementHandler)
                .setResultSetHandler(resultSetHandler)
                .addInitAction(initActions)
                .addLoopAction(loopActions)
                .addDestroyAction(destroyActions);
    }

    protected boolean isMapper(Class mapperClass) {
        return mapperClass.isAnnotationPresent(Mapper.class);
    }

    protected ActionProvider actionProvider(Class mapperClass, Method method) throws InvocationTargetException, IllegalAccessException {
        Provider providerAnnotation = method.getDeclaredAnnotation(Provider.class);
        if (providerAnnotation != null) {
            Class type = providerAnnotation.type();
            String methodName = providerAnnotation.method();
            if (ObjectUtil.isNull(methodName)) {
                methodName = method.getName();
            }
            Method[] methods = type.getMethods();
            for (int i = 0; i < methods.length; i++) {
                String name = methods[i].getName();
                if (name.equals(methodName)) {
                    Class<?> returnType = methods[i].getReturnType();
                    if (returnType == String.class) {
                        Class<?>[] parameterTypes = methods[i].getParameterTypes();
                        if (parameterTypes.length == 0) {
                            Object providerObject = ReflectUtil.create(type);
                            String sql = (String) methods[i].invoke(providerObject);
                            ActionProvider actionProvider = () -> sql;
                            return actionProvider;
                        } else if (parameterTypes.length == 1 && parameterTypes[0] instanceof Class) {
                            Object providerObject = ReflectUtil.create(type);
                            String sql = (String) methods[i].invoke(providerObject, mapperClass);
                            ActionProvider actionProvider = () -> sql;
                            return actionProvider;
                        }
                    } else if (ActionProvider.class.isAssignableFrom(returnType)) {
                        Class<?>[] parameterTypes = methods[i].getParameterTypes();
                        if (parameterTypes.length == 0) {
                            Object providerObject = ReflectUtil.create(type);
                            ActionProvider actionProvider = (ActionProvider) methods[i].invoke(providerObject);
                            return actionProvider;
                        } else if (parameterTypes.length == 1 && parameterTypes[0] instanceof Class) {
                            Object providerObject = ReflectUtil.create(type);
                            ActionProvider actionProvider = (ActionProvider) methods[i].invoke(providerObject, mapperClass);
                            return actionProvider;
                        }
                    }
                }
            }
        }
        return null;
    }

    protected String getSql(Class mapperClass, Method method, ActionProvider actionProvider) {
        String sql = null;
        if (actionProvider != null) {
            sql = actionProvider.sql();
        }
        if (sql == null) {
            Sql sqlAnnotation = method.getDeclaredAnnotation(Sql.class);
            if (sqlAnnotation != null) {
                sql = sqlAnnotation.value();
            }
        }
        return sql;
    }

    protected int getTimeOut(Class mapperClass, Method method, ActionProvider actionProvider) {
        Integer timeOut = null;
        if (actionProvider != null) {
            timeOut = actionProvider.timeOut();
        }
        if (timeOut == null) {
            Sql sqlAnnotation = method.getDeclaredAnnotation(Sql.class);
            if (sqlAnnotation != null) {
                timeOut = sqlAnnotation.timeOut();
            } else {
                timeOut = 0;
            }
        }
        return timeOut;
    }

    protected String getPage(Class mapperClass, Method method, ActionProvider actionProvider) {
        String page = null;
        if (actionProvider != null) {
            page = actionProvider.page();
        }
        if (page == null) {
            PageQuery pageQueryAnnotation = method.getDeclaredAnnotation(PageQuery.class);
            if (pageQueryAnnotation != null) {
                page = pageQueryAnnotation.value();
            }
        }
        return page;
    }

    protected StatementHandler statementHandler(Class mapperClass, Method method, ActionProvider actionProvider) {
        if (actionProvider != null) {
            return actionProvider.statementHandler();
        } else {
            return null;
        }
    }

    protected ResultSetHandler resultSetHandler(Class mapperClass, Method method, ActionProvider actionProvider) {
        if (actionProvider != null) {
            return actionProvider.resultSetHandler();
        } else {
            return null;
        }
    }

    protected String getParamName(Parameter parameter) {
        Param paramAnnotation = parameter.getDeclaredAnnotation(Param.class);
        if (paramAnnotation != null) {
            return paramAnnotation.value();
        } else {
            return null;
        }
    }

    protected Class<? extends Collection> getRowType(Class mapperClass, Method method, ActionProvider actionProvider) {
        Class<? extends Collection> rowType = null;
        if (actionProvider != null) {
            rowType = actionProvider.rowType();
        }
        if (rowType == null) {
            rowType = ReflectUtil.getRowType(mapperClass, method);
        }
        if (rowType == null) {
            rowType = NonCollection.class;
        }
        return rowType;
    }

    protected Class getColType(Class mapperClass, Method method, ActionProvider actionProvider) {
        Class<?> colType = null;
        if (actionProvider != null) {
            colType = actionProvider.colType();
        }
        if (colType == null) {
            colType = ReflectUtil.getColType(mapperClass, method);
        }
        return colType;
    }

    protected InitAction[] initActions(Class mapperClass, Method method, ActionProvider actionProvider) {
        InitAction initAction = null;
        if (actionProvider != null) {
            initAction = actionProvider.initAction();
        }
        if (initAction == null) {
            return null;
        } else {
            return new InitAction[]{initAction};
        }
    }

    protected LoopAction[] loopActions(Class mapperClass, Method method, ActionProvider actionProvider) {
        LoopAction loopAction = null;
        if (actionProvider != null) {
            loopAction = actionProvider.loopAction();
        }
        if (loopAction == null) {
            return null;
        } else {
            return new LoopAction[]{loopAction};
        }
    }

    protected DestroyAction[] destroyActions(Class mapperClass, Method method, ActionProvider actionProvider) {
        DestroyAction destroyAction = null;
        if (actionProvider != null) {
            destroyAction = actionProvider.destroyAction();
        }
        if (destroyAction == null) {
            return null;
        } else {
            return new DestroyAction[]{destroyAction};
        }
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
                    if (paramName == null) {
                        paramName = parameter.getName();
                    }
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
        return ReflectUtil.find(type, classType -> {
            if (classType.isInterface()) {
                return Arrays.asList(classType);
            } else {
                return null;
            }
        }).toArray(new Class[0]);
    }

    @Override
    public <T> T getMapper(Class<T> type, Session session) {
        Class[] typeList = mapperTypeMap.get(type);
        if (typeList == null) {
            return null;
        }
        return (T) Proxy.newProxyInstance(type.getClassLoader(), typeList, (proxy, method, args) -> {
            MethodInfo methodInfo = methodInfoMap.get(getId(method));
            if (methodInfo != null) {
                Map<String, Object> argMap = getArg(methodInfo, args);
                return session.execute(methodInfo, argMap);
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
                    Map<String, Object> paramMap = new HashMap<>(4);
                    paramMap.put(paramName, args[0]);
                    arg = paramMap;
                }
            } else {
                MethodParam[] methodParamList = methodInfo.getMethodParamList();
                Map<String, Object> paramMap = new HashMap<>(4);
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

    protected String getId(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName();
    }
}
