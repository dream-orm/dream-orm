package com.moxa.dream.antlr.handler.crud;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.LimitInvoker;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class LimitHandler extends AbstractHandler {
    private final LimitInvoker limitInvoker;
    private final Statement firstStatement;
    private final Statement secondStatement;

    public LimitHandler(LimitInvoker limitInvoker, Statement firstStatement, Statement secondStatement) {
        this.limitInvoker = limitInvoker;
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        QueryStatement queryStatement = (QueryStatement) statement;
        limitInvoker.addLimit(queryStatement, firstStatement, secondStatement);
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, ToAssist sqlAssist) {
        return statement instanceof QueryStatement;
    }
}
