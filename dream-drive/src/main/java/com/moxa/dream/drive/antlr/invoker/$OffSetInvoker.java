package com.moxa.dream.drive.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.drive.antlr.handler.PageHandler;
import com.moxa.dream.system.mapped.MethodInfo;

import java.util.List;

public class $OffSetInvoker extends AbstractInvoker {
    PageHandler pageHandler;

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        pageHandler = new PageHandler(this, methodInfo);
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        pageHandler.setParamList(columnList[1], columnList[2], true);
        String sql = toSQL.toStr(columnList[0], assist, invokerList);
        invokerStatement.replaceWith(columnList[0]);
        return sql;
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{pageHandler};
    }
}
