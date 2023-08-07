package com.dream.system.antlr.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.ExprUtil;
import com.dream.system.antlr.handler.non.BraceHandler;
import com.dream.system.antlr.handler.non.ConditionHandler;
import com.dream.system.antlr.handler.non.FunctionHandler;
import com.dream.system.antlr.handler.non.MarkHandler;

import java.util.List;

public class NonInvoker extends AbstractInvoker {

    public static final String FUNCTION = "non";

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        String sql = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        if (ExprUtil.isEmpty(sql)) {
            sql = "1=1";
        }
        return sql;
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{new MarkHandler(this), new ConditionHandler(), new BraceHandler(), new FunctionHandler()};
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
        return FUNCTION;
    }
}
