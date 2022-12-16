package com.moxa.dream.system.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.system.antlr.handler.PageHandler;
import com.moxa.dream.system.config.MethodInfo;

import java.util.List;

public class LimitInvoker extends AbstractInvoker {
    public static final String FUNCTION = "limit";
    PageHandler pageHandler;

    @Override
    public Handler[] handler() {
        return new Handler[]{pageHandler};
    }

    @Override
    protected String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        pageHandler = new PageHandler(this, methodInfo);
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        pageHandler.setParamList(columnList[1], columnList[2], false);
        String sql = toSQL.toStr(columnList[0], assist, invokerList);
        invokerStatement.replaceWith(columnList[0]);
        return sql;
    }

    @Override
    public Invoker newInstance() {
        return new LimitInvoker();
    }

    @Override
    public String function() {
        return FUNCTION;
    }
}
