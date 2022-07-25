package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.crud.WhereHandler;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class WhereInvoker extends AbstractInvoker {
    private WhereHandler whereHandler;

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        if (columnList.length == 2) {
            whereHandler = new WhereHandler(this, columnList[1]);
            String sql = toSQL.toStr(columnList[0], assist, invokerList);
            invokerStatement.setStatement(columnList[0]);
            return sql;
        } else
            throw new InvokerException("参数错误，不满足@" + AntlrInvokerFactory.WHERE + ":" + AntlrInvokerFactory.NAMESPACE + "(crud,condition)");
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{whereHandler};
    }

    public void addWhere(QueryStatement queryStatement, Statement statement) {
        WhereStatement whereStatement = new WhereStatement();
        whereStatement.setCondition(statement);
        queryStatement.setWhereStatement(whereStatement);
        this.setAccessible(false);
    }
}
