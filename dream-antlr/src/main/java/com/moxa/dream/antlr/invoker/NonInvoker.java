package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.non.$Handler;
import com.moxa.dream.antlr.handler.non.BraceHandler;
import com.moxa.dream.antlr.handler.non.ConditionHandler;
import com.moxa.dream.antlr.handler.non.FunctionHandler;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.ExprUtil;

import java.util.List;

public class NonInvoker extends AbstractInvoker {

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        String nonResult = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        if (ExprUtil.isEmpty(nonResult))
            nonResult = "1=1";
        return nonResult;
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{new $Handler(), new ConditionHandler(), new BraceHandler(), new FunctionHandler()};
    }
}
