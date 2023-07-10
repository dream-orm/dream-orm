package com.moxa.dream.flex.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.ArrayList;
import java.util.List;

public class FlexValueInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_flex_value";
    private final List<Object> paramList = new ArrayList<>();

    @Override
    public Invoker newInstance() {
        return new FlexValueInvoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        FlexValueInvokerStatement flexValueInvokerStatement = (FlexValueInvokerStatement) invokerStatement;
        Object value = flexValueInvokerStatement.getValue();
        paramList.add(value);
        return "?";
    }

    @Override
    public Handler[] handler() {
        return new Handler[0];
    }

    public List<Object> getParamList() {
        return paramList;
    }
}
