package com.moxa.dream.antlr.handler.crud;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.OffSetInvoker;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class OffSetHandler extends AbstractHandler {
    private final OffSetInvoker offSetInvoker;
    private final Statement firstStatement;
    private final Statement secondStatement;

    public OffSetHandler(OffSetInvoker offSetInvoker, Statement firstStatement, Statement secondStatement) {
        this.offSetInvoker = offSetInvoker;
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        QueryStatement queryStatement = (QueryStatement) statement;
        offSetInvoker.addOffSet(queryStatement, firstStatement, secondStatement);
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof QueryStatement;
    }
}
