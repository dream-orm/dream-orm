package com.moxa.dream.antlr.smt;

import java.util.HashMap;
import java.util.Map;

public class PackageStatement extends Statement {
    private final Map<Class, Object> infoMap = new HashMap<>(4);
    private Statement statement;

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
        if (statement != null) {
            statement.parentStatement = this;
        }
    }

    public <T> void setValue(Class<T> type, T value) {
        infoMap.put(type, value);
    }

    public <T> T getValue(Class<T> type) {
        return (T) infoMap.get(type);
    }

    @Override
    protected Boolean isNeedInnerCache() {
        return isNeedInnerCache(statement);
    }
}
