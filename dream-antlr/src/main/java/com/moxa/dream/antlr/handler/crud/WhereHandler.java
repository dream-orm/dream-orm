package com.moxa.dream.antlr.handler.crud;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.invoker.WhereInvoker;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class WhereHandler extends AbstractHandler {
    private WhereInvoker whereInvoker;
    private Statement statement;

    public WhereHandler(WhereInvoker whereInvoker, Statement statement) {
        this.whereInvoker = whereInvoker;
        this.statement = statement;
    }

    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        QueryStatement queryStatement = (QueryStatement) statement;
        whereInvoker.addWhere(queryStatement, this.statement);
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, ToAssist sqlAssist) {
        return statement instanceof QueryStatement;
    }
}
