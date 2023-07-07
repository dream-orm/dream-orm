package com.moxa.dream.system.provider;

import com.moxa.dream.system.core.action.Action;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;

import java.util.Collection;

public interface ActionProvider {

    String sql();

    default Action[] initActionList() {
        return null;
    }

    default Action[] destroyActionList() {
        return null;
    }

    default Class<? extends Collection> rowType() {
        return null;
    }

    default Class<?> colType() {
        return null;
    }

    default Boolean cache() {
        return null;
    }

    default Integer timeOut() {
        return null;
    }

    default StatementHandler statementHandler() {
        return null;
    }

    default ResultSetHandler resultSetHandler() {
        return null;
    }
}
