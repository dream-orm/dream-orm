package com.moxa.dream.solon.test.provider;

import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.provider.ActionProvider;

import java.util.Collection;


public class BlogActionProvider implements ActionProvider {
    @Override
    public String sql() {
        return "select @*() from blog where user_id=@?(userId)";
    }

    @Override
    public Action[] initActionList() {
        return new Action[]{((session, mappedStatement, arg) -> {
            System.out.println("执行initAction方法");
        })};
    }

    @Override
    public Action[] destroyActionList() {
        return new Action[]{((session, mappedStatement, arg) -> {
            System.out.println("执行destroyAction方法");
        })};
    }

    @Override
    public Class<? extends Collection> rowType() {
        return ActionProvider.super.rowType();
    }

    @Override
    public Class<?> colType() {
        return ActionProvider.super.colType();
    }

    @Override
    public Boolean cache() {
        return ActionProvider.super.cache();
    }

    @Override
    public Integer timeOut() {
        return ActionProvider.super.timeOut();
    }


    @Override
    public StatementHandler statementHandler() {
        return ActionProvider.super.statementHandler();
    }

    @Override
    public ResultSetHandler resultSetHandler() {
        return ActionProvider.super.resultSetHandler();
    }
}
