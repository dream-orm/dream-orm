package com.dream.system.antlr.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.Handler;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.antlr.handler.emit.EmitColonHandler;

import java.util.List;

public class EmitInvoker extends AbstractInvoker {
    public static final String FUNCTION = "emit";

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        ListColumnStatement paramStatement = (ListColumnStatement) invokerStatement.getParamStatement();
        Statement[] columnList = paramStatement.getColumnList();
        String sql = toSQL.toStr(columnList[0], assist, invokerList);
        invokerStatement.replaceWith(columnList[0]);
        return sql;
    }

    @Override
    public String function() {
        return FUNCTION;
    }

    @Override
    protected Handler[] handler() {
        return new Handler[]{new EmitColonHandler()};
    }
}
