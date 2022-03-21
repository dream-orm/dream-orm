package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.core.statementhandler.StatementHandler;

import java.sql.Connection;

public class DeleteExecutorHandler extends AbstractExecutorHandler {
    public DeleteExecutorHandler(StatementHandler statementHandler, Connection connection) {
        super(statementHandler, connection);
    }
}
