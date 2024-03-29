package com.dream.flex.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;

import java.util.ArrayList;
import java.util.List;

public class FlexMarkInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_flex_mark";
    private final List<Object> paramList = new ArrayList<>();

    @Override
    public Invoker newInstance() {
        return new FlexMarkInvoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        FlexMarkInvokerStatement flexMarkInvokerStatement = (FlexMarkInvokerStatement) invokerStatement;
        Object value = flexMarkInvokerStatement.getValue();
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
