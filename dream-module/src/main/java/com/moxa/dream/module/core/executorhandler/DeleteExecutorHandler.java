package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.core.statementhandler.StatementHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class DeleteExecutorHandler extends UpdateExecutorHandler {
    public DeleteExecutorHandler(StatementHandler statementHandler, Connection connection) throws SQLException {
        super(statementHandler, connection);
    }
}
