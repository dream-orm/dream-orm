package com.dream.system.config;

import com.dream.antlr.smt.PackageStatement;
import com.dream.system.cache.CacheKey;
import com.dream.system.core.action.DestroyAction;
import com.dream.system.core.action.InitAction;
import com.dream.system.core.action.LoopAction;
import com.dream.system.core.resultsethandler.ResultSetHandler;
import com.dream.system.core.statementhandler.StatementHandler;
import com.dream.system.typehandler.handler.TypeHandler;

import java.lang.reflect.Method;
import java.util.*;

public class MappedStatement {

    protected Command command;
    protected String sql;
    protected Set<String> tableSet;
    protected List<MappedParam> mappedParamList;
    protected MethodInfo methodInfo;
    protected CacheKey uniqueKey;
    protected Object arg;
    protected Map<String, Object> envMap;

    protected MappedStatement() {

    }

    public Configuration getConfiguration() {
        return methodInfo.getConfiguration();
    }

    public Command getCommand() {
        return command;
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
        return sql;
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

    public Method getMethod() {
        return methodInfo.getMethod();
    }

    public CacheKey getMethodKey() {
        return methodInfo.getMethodKey();
    }

    public Set<String> getTableSet() {
        return tableSet;
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

    public InitAction[] getInitActionList() {
        return methodInfo.getInitActionList();
    }

    public LoopAction[] getLoopActionList() {
        return methodInfo.getLoopActionList();
    }

    public DestroyAction[] getDestroyActionList() {
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
            return this;
        }

        public Builder command(Command command) {
            mappedStatement.command = command;
            return this;
        }

        public Builder sql(String sql) {
            mappedStatement.sql = sql;
            return this;
        }

        public Builder tableSet(Set<String> tableSet) {
            mappedStatement.tableSet = tableSet;
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
