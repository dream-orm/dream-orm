package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.OrderStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class AscInvoker extends AbstractInvoker {
    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = invokerStatement.getListColumnStatement().getColumnList();
        if (columnList.length == 1) {
            OrderStatement.AscStatement ascStatement = new OrderStatement.AscStatement(columnList[0]);
            invokerStatement.setStatement(ascStatement);
            String sql = toSQL.toStr(ascStatement, assist, invokerList);
            return sql;
        } else
            throw new InvokerException("参数个数错误，不满足@" + AntlrInvokerFactory.ASC + ":" + AntlrInvokerFactory.NAMESPACE + "(column)");
    }
}