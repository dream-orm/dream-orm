package com.dream.struct.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.config.MappedParam;

import java.util.ArrayList;
import java.util.List;

public class TakeMarkInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_regular_take_mark";
    private final List<MappedParam> mappedParamList = new ArrayList<>();

    @Override
    public Invoker newInstance() {
        return new TakeMarkInvoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        TakeMarkInvokerStatement takeMarkInvokerStatement = (TakeMarkInvokerStatement) invokerStatement;
        mappedParamList.add(takeMarkInvokerStatement.getMappedParam());
        return "?";
    }

    @Override
    public Handler[] handler() {
        return new Handler[0];
    }

    public List<MappedParam> getMappedParamList() {
        return mappedParamList;
    }
}
