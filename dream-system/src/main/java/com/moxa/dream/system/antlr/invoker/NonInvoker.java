package com.moxa.dream.system.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.system.antlr.handler.non.$Handler;
import com.moxa.dream.system.antlr.handler.non.BraceHandler;
import com.moxa.dream.system.antlr.handler.non.ConditionHandler;
import com.moxa.dream.system.antlr.handler.non.FunctionHandler;

import java.util.List;

public class NonInvoker extends AbstractInvoker {

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        return toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{new $Handler(this), new ConditionHandler(), new BraceHandler(), new FunctionHandler()};
    }

    public boolean isEmpty(Object value) {
        return value == null;
    }

    @Override
    public Invoker newInstance() {
        return this;
    }

    @Override
    public String function() {
        return "non";
    }
}
