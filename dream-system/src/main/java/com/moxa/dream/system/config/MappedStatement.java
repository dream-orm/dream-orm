package com.moxa.dream.system.config;

import com.moxa.dream.antlr.smt.PackageStatement;
import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;

import java.lang.reflect.Method;
import java.util.*;

public class MappedStatement {

    protected MappedSql mappedSql;
    protected List<MappedParam> mappedParamList;
    protected MethodInfo methodInfo;
    protected CacheKey uniqueKey;
    protected Object arg;
    protected boolean cache;
    protected Map<String, Object> envMap;

    protected MappedStatement() {

    }

    public Configuration getConfiguration() {
        return methodInfo.getConfiguration();
    }

    public Command getCommand() {
        return mappedSql.getCommand();
    }

    public String getId() {
        return methodInfo.getId();
    }

    public PackageStatement getStatement() {
        return methodInfo.getStatement();
    }

    public Object getArg() {
        return arg;
    }

    public String getSql() {
        return mappedSql.getSql();
    }

    public CacheKey getUniqueKey() {
        return uniqueKey;
    }

    public Class<?> getColType() {
        return methodInfo.getColType();
    }

    public Class<? extends Collection> getRowType() {
        return methodInfo.getRowType();
    }

    public String[] getColumnNames() {
        return methodInfo.getColumnNames();
    }

    public TypeHandler[] getColumnTypeHandlers() {
        return methodInfo.getColumnTypeHandlers();
    }

    public boolean isCache() {
        return cache;
    }

    public MappedStatement setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public Method getMethod() {
        return methodInfo.getMethod();
    }

    public CacheKey getMethodKey() {
        return methodInfo.getMethodKey();
    }

    public Set<String> getTableSet() {
        return mappedSql.getTableSet();
    }

    public int getTimeOut() {
        return methodInfo.getTimeOut();
    }

    public <T> T get(Class<T> type) {
        return methodInfo.get(type);
    }

    public <T> MappedStatement set(Class<T> type, T value) {
        methodInfo.set(type, value);
        return this;
    }

    public List<MappedParam> getMappedParamList() {
        return mappedParamList;
    }

    public Action[] getInitActionList() {
        return methodInfo.getInitActionList();
    }

    public Action[] getLoopActionList() {
        return methodInfo.getLoopActionList();
    }

    public Action[] getDestroyActionList() {
        return methodInfo.getDestroyActionList();
    }

    public StatementHandler getStatementHandler() {
        return methodInfo.getStatementHandler();
    }

    public ResultSetHandler getResultSetHandler() {
        return methodInfo.getResultSetHandler();
    }

    public MappedStatement put(String key, Object value) {
        if (envMap == null) {
            envMap = new HashMap<>(4);
        }
        envMap.put(key, value);
        return this;
    }

    public Object get(String key) {
        if (envMap == null) {
            return null;
        } else {
            return envMap.get(key);
        }
    }

    public static class Builder {
        protected final MappedStatement mappedStatement;

        public Builder() {
            mappedStatement = new MappedStatement();
        }

        public Builder methodInfo(MethodInfo methodInfo) {
            mappedStatement.methodInfo = methodInfo;
            mappedStatement.cache = methodInfo.isCache();
            return this;
        }

        public Builder mappedSql(MappedSql mappedSql) {
            mappedStatement.mappedSql = mappedSql;
            return this;
        }

        public Builder mappedParamList(List<MappedParam> mappedParamList) {
            mappedStatement.mappedParamList = mappedParamList;
            return this;
        }

        public Builder arg(Object arg) {
            mappedStatement.arg = arg;
            return this;
        }

        public Builder uniqueKey(CacheKey uniqueKey) {
            mappedStatement.uniqueKey = uniqueKey;
            return this;
        }

        public MappedStatement build() {
            return mappedStatement;
        }
    }
}
