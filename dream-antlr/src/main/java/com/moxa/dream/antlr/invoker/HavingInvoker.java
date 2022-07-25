package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.crud.HavingHandler;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class HavingInvoker extends AbstractInvoker {
    private HavingHandler havingHandler;

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        if (columnList.length == 2) {
            havingHandler = new HavingHandler(this, columnList[1]);
            String sql = toSQL.toStr(columnList[0], assist, invokerList);
            invokerStatement.setStatement(columnList[0]);
            return sql;
        } else
            throw new InvokerException("参数个数错误，不满足" + AntlrInvokerFactory.HAVING + ":" + AntlrInvokerFactory.NAMESPACE + "(crud,condition)");
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{havingHandler};
    }

    public void addHaving(QueryStatement queryStatement, Statement statement) {
        HavingStatement havingStatement = new HavingStatement();
        havingStatement.setCondition(statement);
        queryStatement.setHavingStatement(havingStatement);
        this.setAccessible(false);
    }
}
