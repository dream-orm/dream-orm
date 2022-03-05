package com.moxa.dream.driver.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.driver.handler.PageHandler;
import com.moxa.dream.module.mapper.MethodInfo;

import java.util.List;

public class $OffSetInvoker extends AbstractInvoker {
    PageHandler pageHandler;

    @Override
    public void init(ToAssist assist) {
        MethodInfo methodInfo = assist.getCustom(MethodInfo.class);
        pageHandler = new PageHandler(this, methodInfo);
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = invokerStatement.getListColumnStatement().getColumnList();
        pageHandler.setParamList(columnList[1], columnList[2], true);
        String sql = toSQL.toStr(columnList[0], assist, invokerList);
        invokerStatement.setStatement(columnList[0]);
        return sql;
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{pageHandler};
    }
}
