package com.moxa.dream.antlr.handler.crud;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.GroupInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class GroupHandler extends AbstractHandler {
    private final GroupInvoker groupInvoker;
    private final Statement[] statementList;

    public GroupHandler(GroupInvoker groupInvoker, Statement[] statementList) {
        this.groupInvoker = groupInvoker;
        this.statementList = statementList;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        QueryStatement queryStatement = (QueryStatement) statement;
        groupInvoker.addGroup(queryStatement, statementList);
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof QueryStatement;
    }
}
