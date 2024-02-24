package com.dream.mate.transform.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.transform.handler.LetterHandler;
import com.dream.mate.transform.inject.TransformHandler;

import java.util.List;

public class TransformInvoker extends AbstractInvoker {
    public static final String FUNCTION = "dream_mate_block";
    private TransformHandler transformHandler;

    public TransformInvoker(TransformHandler transformHandler) {
        this.transformHandler = transformHandler;
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement statement = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList()[0];
        String sql = toSQL.toStr(invokerStatement.getParamStatement(), assist, invokerList);
        invokerStatement.replaceWith(statement);
        return sql;
    }

    @Override
    protected Handler[] handler() {
        return new Handler[]{new LetterHandler(this)};
    }

    public boolean intercept(String column, List<Invoker> invokerList) {
        return transformHandler.intercept(column, invokerList);
    }


    @Override
    public String function() {
        return FUNCTION;
    }

}

