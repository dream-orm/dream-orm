package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.CustomFunctionStatement;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;

import java.util.ArrayList;
import java.util.List;

public class ToPubSQL extends ToDREAM {

    protected String beforeCache(Statement statement) {
        String sql = statement.getQuickValue();
        if (sql != null)
            return sql;
        else
            return null;
    }

    protected void afterCache(Statement statement, String sql) {
        if (statement.isNeedCache())
            statement.setQuickValue(sql);
    }

    @Override
    protected String toString(InvokerStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        Invoker invoker = assist.getInvoker(statement.getNamespace(), statement.getFunction());
        if (invokerList == null)
            invokerList = new ArrayList<>();
        return invoker.invoke(statement, assist, this, invokerList);
    }

    @Override
    protected String toString(CustomFunctionStatement statement, ToAssist assist, List<Invoker> invokerList) throws InvokerException {
        return statement.toString(this, assist, invokerList);
    }
}