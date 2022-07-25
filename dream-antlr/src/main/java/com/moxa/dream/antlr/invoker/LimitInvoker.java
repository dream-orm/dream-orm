package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.crud.LimitHandler;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class LimitInvoker extends AbstractInvoker {
    private LimitHandler limitHandler;

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        Statement secondStatement = null;
        if (columnList.length == 2 || (columnList.length == 3 && (secondStatement = columnList[2]) != null)) {
            Statement firstStatement = columnList[1];
            limitHandler = new LimitHandler(this, firstStatement, secondStatement);
            String sql = toSQL.toStr(columnList[0], assist, invokerList);
            invokerStatement.setStatement(columnList[0]);
            return sql;
        } else
            throw new InvokerException("参数个数错误，不满足@" + AntlrInvokerFactory.NAMESPACE + ":" + AntlrInvokerFactory.LIMIT + "(query,first,second?)");
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{limitHandler};
    }

    public void addLimit(QueryStatement queryStatement, Statement firstStatement, Statement secondStatement) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setFirst(firstStatement);
        limitStatement.setSecond(secondStatement);
        queryStatement.setLimitStatement(limitStatement);
        this.setAccessible(false);
    }
}
