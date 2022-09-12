package com.moxa.dream.drive.mapper;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.drive.annotation.*;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.mapper.MethodInfo;
import com.moxa.dream.system.mapper.factory.MapperFactory;
import com.moxa.dream.system.mapper.invoke.MapperInvoke;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.reflection.util.NonCollection;
import com.moxa.dream.util.reflection.util.NullObject;

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
                    String name = method.getName();
                    if (builderMap.containsKey(name)) {
                        throw new DreamRunTimeException("方法名'" + name + "'重复定义");
                    }
                    MethodInfo.Builder builder = createMethodInfoBuilder(configuration, mapperClass, method);
                    builderMap.put(name, builder);
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
        boolean batch = isBatch(mapperClass, method);
        Command command = getCommand(mapperClass, method);
        String[] paramNameList = getParamNameList(method);
        String sql = getSql(configuration, method);
        int timeOut = getTimeOut(method);
        Action[] initActionList = getInitActionList(configuration, method);
        Action[] loopActionList = getLoopActionList(configuration, method);
        Action[] destroyActionList = getDestroyActionList(configuration, method);
        MethodInfo.Builder builder = new MethodInfo.Builder(configuration)
                .name(method.getName())
                .rowType(rowType)
                .colType(colType)
                .columnNames(columnNames)
                .cache(cache)
                .batch(batch)
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


    protected Action[] getInitActionList(Configuration configuration, Method method) {
        ActionProvider actionProviderAnnotation = method.getDeclaredAnnotation(ActionProvider.class);
        Action[] actionList = null;
        if (actionProviderAnnotation != null) {
            actionList = ReflectUtil.create(actionProviderAnnotation.value()).init(configuration, method);
        }
        return actionList;
    }

    protected Action[] getLoopActionList(Configuration configuration, Method method) {
        ActionProvider actionProviderAnnotation = method.getDeclaredAnnotation(ActionProvider.class);
        Action[] actionList = null;
        if (actionProviderAnnotation != null) {
            actionList = ReflectUtil.create(actionProviderAnnotation.value()).loop(configuration, method);
        }
        return actionList;
    }

    protected Action[] getDestroyActionList(Configuration configuration, Method method) {
        ActionProvider actionProviderAnnotation = method.getDeclaredAnnotation(ActionProvider.class);
        Action[] actionList = null;
        if (actionProviderAnnotation != null) {
            actionList = ReflectUtil.create(actionProviderAnnotation.value()).destroy(configuration, method);
        }
        return actionList;
    }

    protected boolean isMapper(Class mapperClass) {
        return mapperClass.isAnnotationPresent(Mapper.class);
    }

    protected String getSql(Configuration configuration, Method method) {
        String sql = null;
        Sql sqlAnnotation = method.getDeclaredAnnotation(Sql.class);
        if (sqlAnnotation == null) {
            ActionProvider actionProviderAnnotation = method.getDeclaredAnnotation(ActionProvider.class);
            if (actionProviderAnnotation != null) {
                sql = ReflectUtil.create(actionProviderAnnotation.value()).value(configuration, method);
            }
        } else {
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

    protected String[] getColumnNames(Class mapperClass, Method method) {
        Setup setupAnnotation = method.getDeclaredAnnotation(Setup.class);
        if (setupAnnotation != null) {
            String[] columnNames = setupAnnotation.columnNames();
            if (!ObjectUtil.isNull(columnNames)) {
                return columnNames;
            }
        }
        return new String[0];
    }

    protected boolean isCache(Class mapperClass, Method method) {
        Setup setupAnnotation = method.getDeclaredAnnotation(Setup.class);
        boolean cache = true;
        if (setupAnnotation != null) {
            cache = setupAnnotation.cache();
        }
        return cache;
    }

    protected boolean isBatch(Class mapperClass, Method method) {
        Setup setupAnnotation = method.getDeclaredAnnotation(Setup.class);
        boolean batch = false;
        if (setupAnnotation != null) {
            batch = setupAnnotation.batch();
        }
        return batch;
    }

    protected Command getCommand(Class mapperClass, Method method) {
        Setup setupAnnotation = method.getDeclaredAnnotation(Setup.class);
        Command command = Command.NONE;
        if (setupAnnotation != null) {
            command = setupAnnotation.command();
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
        ObjectUtil.requireNonNull(typeList, "类 '" + type.getName() + "'未在Mapper注册");
        return (T) Proxy.newProxyInstance(type.getClassLoader(), typeList, (proxy, method, args) -> {
            MethodInfo methodInfo = methodInfoMap.get(method);
            if (methodInfo != null) {
                Object arg = getArg(methodInfo, args);
                return mapperInvoke.invoke(methodInfo, arg);
            } else {
                return mapperInvoke.invoke(type, proxy, method, args);
            }
        });
    }

    protected Object getArg(MethodInfo methodInfo, Object[] args) {
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
        return arg;
    }

    @Override
    public Collection<Class> getMapperTypeList() {
        return mapperTypeMap.keySet();
    }
}
