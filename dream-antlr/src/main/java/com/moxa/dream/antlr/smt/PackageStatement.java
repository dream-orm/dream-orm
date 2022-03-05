package com.moxa.dream.antlr.smt;

import com.moxa.dream.util.common.ObjectUtil;

import java.util.HashMap;
import java.util.Map;

public class PackageStatement extends Statement {
    private Statement statement;
    private Map<Class, Object> infoMap = new HashMap<>();

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
        if (statement != null)
            statement.parentStatement = this;
    }

    public <T> void setValue(Class<T> type, T value) {
        ObjectUtil.requireNonNull(type, "Property 'type' is required");
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
