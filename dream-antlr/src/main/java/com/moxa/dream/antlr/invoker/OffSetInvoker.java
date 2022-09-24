package com.moxa.dream.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.Handler;
import com.moxa.dream.antlr.handler.crud.OffSetHandler;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class OffSetInvoker extends AbstractInvoker {
    private OffSetHandler offSetHandler;

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        Statement secondStatement = null;
        if (columnList.length == 2 || (columnList.length == 3 && (secondStatement = columnList[2]) != null)) {
            Statement firstStatement = columnList[1];
            offSetHandler = new OffSetHandler(this, firstStatement, secondStatement);
            String sql = toSQL.toStr(columnList[0], assist, invokerList);
            invokerStatement.replaceWith(columnList[0]);
            return sql;
        } else
            throw new InvokerException("参数个数错误，不满足@" + AntlrInvokerFactory.OFFSET + ":" + AntlrInvokerFactory.NAMESPACE + "(query,first,second?)");
    }

    @Override
    public Handler[] handler() {
        return new Handler[]{offSetHandler};
    }

    public void addOffSet(QueryStatement queryStatement, Statement firstStatement, Statement secondStatement) {
        LimitStatement limitStatement = new LimitStatement();
        limitStatement.setOffset(true);
        limitStatement.setFirst(firstStatement);
        limitStatement.setSecond(secondStatement);
        queryStatement.setLimitStatement(limitStatement);
        this.setAccessible(false);
    }
}
