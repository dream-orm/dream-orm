package com.moxa.dream.system.mapper;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.dialect.DialectFactory;
import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodInfo {
    private final Map<Class, Object> builtMap = new HashMap<>();
    private Configuration configuration;
    private String name;
    private Class<? extends Collection> rowType;
    private Class colType;
    private String sql;
    private Integer timeOut;
    private List<EachInfo> eachInfoList;
    private String[] paramNameList;
    private PackageStatement statement;
    private CacheKey sqlKey;
    private Method method;
    private boolean generatedKeys;

    private MethodInfo() {

    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Collection> getRowType() {
        return rowType;
    }

    public Class getColType() {
        return colType;
    }

    public String getSql() {
        return sql;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public List<EachInfo> getEachInfoList() {
        return eachInfoList;
    }

    public String[] getParamNameList() {
        return paramNameList;
    }

    public PackageStatement getStatement() {
        return statement;
    }

    public CacheKey getSqlKey() {
        return sqlKey.clone();
    }

    public Method getMethod() {
        return method;
    }

    public boolean isGeneratedKeys() {
        return generatedKeys;
    }

    public String getId() {
        if (method == null)
            return "";
        else
            return method.getDeclaringClass().getName() + "." + method.getName();
    }

    public <T> void set(Class<T> type, T value) {
        builtMap.put(type, value);
    }

    public <T> T get(Class<T> type) {
        return (T) builtMap.get(type);
    }

    public static class Builder {
        private final MethodInfo methodInfo;

        public Builder(Configuration configuration) {
            methodInfo = new MethodInfo();
            methodInfo.configuration = configuration;
        }

        public Builder name(String name) {
            methodInfo.name = name;
            return this;
        }

        public Builder rowType(Class<? extends Collection> rowType) {
            methodInfo.rowType = rowType;
            return this;
        }

        public Builder colType(Class colType) {
            methodInfo.colType = colType;
            return this;
        }

        public Builder generatedKeys(boolean generatedKeys) {
            methodInfo.generatedKeys = generatedKeys;
            return this;
        }

        public Builder paramNameList(String[] paramNameList) {
            methodInfo.paramNameList = paramNameList;
            return this;
        }

        public Builder sql(String sql) {
            methodInfo.sql = sql;
            return this;
        }

        public Builder timeOut(Integer timeOut) {
            methodInfo.timeOut = timeOut;
            return this;
        }

        public Builder eachInfoList(List<EachInfo> eachInfoList) {
            methodInfo.eachInfoList = eachInfoList;
            return this;
        }

        public Builder method(Method method) {
            methodInfo.method = method;
            return this;
        }

        public MethodInfo build() {
            if (!ObjectUtil.isNull(methodInfo.sql)) {
                DialectFactory dialectFactory = methodInfo.configuration.getDialectFactory();
                ObjectUtil.requireNonNull(dialectFactory, "Property 'dialectFactory' is required");
                methodInfo.statement = dialectFactory.compile(methodInfo);
                ObjectUtil.requireNonNull(methodInfo.statement, "Property 'statement' is required");
                CacheKey sqlKey = dialectFactory.getCacheKey(methodInfo);
                ObjectUtil.requireNonNull(sqlKey, "Property 'sqlKey' is required");
                methodInfo.sqlKey = sqlKey;
                dialectFactory.decoration(methodInfo);
            }
            return methodInfo;
        }
    }
}
