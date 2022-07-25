package com.moxa.dream.antlr.invoker;


import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.crud.GroupHandler;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class GroupInvoker extends AbstractInvoker {
    private GroupHandler groupHandler;

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        if (columnList.length > 1) {
            groupHandler = new GroupHandler(this, columnList);
            String sql = toSQL.toStr(columnList[0], assist, invokerList);
            invokerStatement.setStatement(columnList[0]);
            return sql;
        } else
            throw new InvokerException("参数个数错误，不满足@" + AntlrInvokerFactory.GROUP + ":" + AntlrInvokerFactory.NAMESPACE + "(query,column1,column2,...,columnN)");
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{groupHandler};
    }

    public void addGroup(QueryStatement queryStatement, Statement[] statementList) {
        GroupStatement groupStatement = new GroupStatement();
        ListColumnStatement listColumnStatement = new ListColumnStatement();
        for (int i = 1; i < statementList.length; i++) {
            listColumnStatement.add(statementList[i]);
        }
        groupStatement.setGroup(listColumnStatement);
        queryStatement.setGroupStatement(groupStatement);
        this.setAccessible(false);
    }
}
