package com.moxa.dream.module.hold.mapper;

import com.moxa.dream.module.hold.annotation.Mapper;
import com.moxa.dream.module.hold.annotation.Param;
import com.moxa.dream.module.hold.annotation.Result;
import com.moxa.dream.module.hold.annotation.Sql;
import com.moxa.dream.module.hold.config.Configuration;
import com.moxa.dream.module.reflect.util.NonCollection;
import com.moxa.dream.module.reflect.util.NullObject;
import com.moxa.dream.module.xml.builder.XMLBuilder;
import com.moxa.dream.module.xml.builder.mapper.MapperInfoBuilder;
import com.moxa.dream.module.xml.moudle.XmlCallback;
import com.moxa.dream.module.xml.moudle.XmlHandler;
import com.moxa.dream.module.xml.moudle.XmlParser;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.BaseReflectHandler;
import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.resource.ResourceUtil;
import org.xml.sax.InputSource;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.*;


public class DefaultMapperFactory implements MapperFactory {
    protected Map<Method, MethodInfo> methodInfoMap = new HashMap<>();
    protected Map<Class, Class[]> mapperTypeMap = new HashMap<>();

    @Override
    public void addMapper(Configuration configuration, Class mapperClass) {
        if (isMapper(mapperClass)) {
            if (mapperTypeMap.containsKey(mapperClass)) {
                return;
            }
            ObjectUtil.requireNonNull(configuration, "Property 'configuration' is required");
            Map<String, MethodInfo.Builder> builderMap = new HashMap<>();
            List<Method> methodList = ReflectUtil.findMethod(mapperClass);
            if (!ObjectUtil.isNull(methodList)) {
                for (Method method : methodList) {
                    MethodInfo.Builder builder = createMethodInfoBuilder(configuration, mapperClass, method);
                    builderMap.put(method.getName(), builder);
                }
            }

            String resource = getResource(mapperClass);
            if (!ObjectUtil.isNull(resource)) {
                wrapperMethodInfoBuilder(configuration, resource, builderMap);
            }
            for (String name : builderMap.keySet()) {
                MethodInfo.Builder builder = builderMap.get(name);
                MethodInfo methodInfo = builder.build();
                if (methodInfo.getStatement() != null) {
                    methodInfoMap.put(methodInfo.getMethod(), methodInfo);
                }
            }
            this.mapperTypeMap.put(mapperClass, getAllInterface(mapperClass));
        }
    }

    protected void wrapperMethodInfoBuilder(Configuration configuration, String resource, Map<String, MethodInfo.Builder> builderMap) {
        InputSource inputSource = new InputSource(ResourceUtil.getResourceAsStream(resource));
        XmlParser xmlParser = new XmlParser();
        xmlParser.parse(inputSource, new XmlCallback() {
            @Override
            public XMLBuilder startDocument(XmlHandler xmlHandler) {
                return new MapperInfoBuilder(configuration, xmlHandler, builderMap);
            }

            @Override
            public void endDocument(Object value) {
            }
        });

    }

    protected MethodInfo.Builder createMethodInfoBuilder(Configuration configuration, Class mapperClass, Method method) {
        Class<? extends Collection> rowType = getRowType(mapperClass, method);
        Class colType = getColType(mapperClass, method);
        String[] paramNameList = getParamNameList(method);
        String sql = getSql(method);
        Integer timeOut = getTimeOut(method);
        List<EachInfo> eachInfoList = getEachInfoList(method);
        MethodInfo.Builder builder = new MethodInfo.Builder(configuration)
                .name(method.getName())
                .rowType(rowType)
                .colType(colType)
                .paramNameList(paramNameList)
                .sql(sql)
                .timeOut(timeOut)
                .eachInfoList(eachInfoList)
                .method(method);
        return builder;
    }

    protected List<EachInfo> getEachInfoList(Method method) {
        return null;
    }

    protected boolean isMapper(Class mapperClass) {
        return mapperClass.isAnnotationPresent(Mapper.class);
    }

    public String getResource(Class mapperClass) {
        Mapper mapperAnnotation = (Mapper) mapperClass.getDeclaredAnnotation(Mapper.class);
        if (mapperAnnotation == null)
            return null;
        String resource = mapperAnnotation.value();
        return resource;
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
        else {
            return parameter.getName();
        }
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
            colType = resultAnnotation.rowType();
            if (colType == NullObject.class) {
                colType = ReflectUtil.getColType(mapperClass, method);
            }
        }
        return colType;
    }

    protected String[] getParamNameList(Method method) {
        Parameter[] parameters = method.getParameters();
        if (parameters != null && parameters.length > 0) {
            String[] paramList = new String[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                paramList[i] = getParamName(parameter);
            }
            return paramList;
        }
        return null;
    }

    protected Class[] getAllInterface(Class type) {
        ObjectUtil.requireNonNull(type, "Property 'type' is required");
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
        ObjectUtil.requireNonNull(typeList, "Class '" + type.getName() + "' was not registered");
        return (T) Proxy.newProxyInstance(type.getClassLoader(), typeList, (proxy, method, args) -> {
                    MethodInfo methodInfo = methodInfoMap.get(method);
                    ObjectUtil.requireNonNull(methodInfo, "Class method name '" + method.getDeclaringClass().getName() + "." + method.getName() + "' was not generate 'methodInfo'");
                    if (ObjectUtil.isNull(args)) {
                        return mapperHandler.invoke(methodInfo, null);
                    } else {
                        String[] paramNameList = methodInfo.getParamNameList();
                        ObjectUtil.requireTrue(!ObjectUtil.isNull(paramNameList) && paramNameList.length == args.length, "Parameter name not match the number of parameter values,please check the class method name '" + methodInfo.getId() + "'");
                        Map<String, Object> paramMap = new HashMap<>();
                        for (int i = 0; i < paramNameList.length; i++) {
                            paramMap.put(paramNameList[i], args[i]);
                        }
                        return mapperHandler.invoke(methodInfo, paramMap);
                    }
                }
        );
    }

    @Override
    public Collection<Class> getMapperTypeList() {
        return mapperTypeMap.keySet();
    }
}
