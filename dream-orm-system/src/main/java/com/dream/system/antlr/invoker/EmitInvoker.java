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
import com.dream.system.antlr.handler.emit.EmitHandler;

import java.util.List;

public class EmitInvoker extends AbstractInvoker {
    public static final String FUNCTION = "emit";

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        ListColumnStatement paramStatement = (ListColumnStatement) invokerStatement.getParamStatement();
        Statement statement = paramStatement.getColumnList()[0];
        String sql = toSQL.toStr(statement, assist, invokerList);
        invokerStatement.replaceWith(statement);
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
        return new Handler[]{new EmitHandler()};
    }
}
