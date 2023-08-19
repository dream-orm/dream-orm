package com.dream.mate.dynamic.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.dynamic.handler.DynamicDeleteHandler;
import com.dream.mate.dynamic.handler.DynamicInsertHandler;
import com.dream.mate.dynamic.handler.DynamicQueryHandler;
import com.dream.mate.dynamic.handler.DynamicUpdateHandler;

import java.util.List;

public class DynamicInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_dynamic";

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        String sql = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        invokerStatement.replaceWith(invokerStatement.getParamStatement());
        return sql;
    }

    @Override
    public Invoker newInstance() {
        return this;
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    protected Handler[] handler() {
        return new Handler[]{new DynamicQueryHandler(), new DynamicUpdateHandler(), new DynamicInsertHandler(), new DynamicDeleteHandler()};
    }
}
