package com.moxa.dream.antlr.bind;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.HashMap;
import java.util.Map;

public class ResultInfo {
    private final String sql;
    private final Map<Class, Invoker> sqlInvokerMap = new HashMap<>();

    public ResultInfo(String sql, Map<String, Invoker> sqlInvokerMap) {
        this.sql = sql;
        if (!ObjectUtil.isNull(sqlInvokerMap)) {
            sqlInvokerMap.values().stream().forEach(sqlInvoker -> this.sqlInvokerMap.put(sqlInvoker.getClass(), sqlInvoker));
        }
    }

    public <T extends Invoker> T getSqlInvoker(Class<T> type) {
        return (T) sqlInvokerMap.get(type);
    }

    public String getSql() {
        return sql;
    }
}
