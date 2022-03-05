package com.moxa.dream.antlr.sql;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.CustomFunctionStatement;
import com.moxa.dream.antlr.smt.InvokerStatement;

import java.util.ArrayList;
import java.util.List;

public class ToPubSQL extends ToDREAM {
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