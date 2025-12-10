package com.dream.flex.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.flex.statement.TakeMarkInvokerStatement;
import com.dream.system.antlr.invoker.MarkInvoker;

import java.util.List;

public class TakeMarkInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_flex_take_mark";

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        TakeMarkInvokerStatement takeMarkInvokerStatement = (TakeMarkInvokerStatement) invokerStatement;
        MarkInvoker markInvoker = (MarkInvoker) assist.getInvoker(MarkInvoker.FUNCTION);
        markInvoker.getParamInfoList().add(new MarkInvoker.ParamInfo(takeMarkInvokerStatement.getColumn(), takeMarkInvokerStatement.getValue()));
        return "?";
    }

}
