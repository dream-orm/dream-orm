package com.moxa.dream.antlr.callback;

import com.moxa.dream.antlr.expr.CompareExpr;
import com.moxa.dream.antlr.read.ExprReader;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCallback implements Callback {
    private Map<String, Statement> cacheMap = new HashMap<>();

    @Override
    public Statement call(String className, String methodName, String[] params, Object[] args) {
        String value = calling(className, methodName, params, args);
        if (ObjectUtil.isNull(value))
            return null;
        Statement statement = cacheMap.get(value);
        if (statement == null) {
            CompareExpr compareExpr = new CompareExpr(new ExprReader(value));
            statement = compareExpr.expr();
            cacheMap.put(value, statement);
        }
        return statement;
    }

    protected abstract String calling(String className, String methodName, String[] params, Object[] args);
}
