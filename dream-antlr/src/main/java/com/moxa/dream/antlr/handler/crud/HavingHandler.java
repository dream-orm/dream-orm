package com.moxa.dream.antlr.handler.crud;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.HavingInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class HavingHandler extends AbstractHandler {
    private final HavingInvoker havingInvoker;
    private final Statement statement;

    public HavingHandler(HavingInvoker havingInvoker, Statement statement) {
        this.havingInvoker = havingInvoker;
        this.statement = statement;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        QueryStatement queryStatement = (QueryStatement) statement;
        havingInvoker.addHaving(queryStatement, this.statement);
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof QueryStatement;
    }
}
