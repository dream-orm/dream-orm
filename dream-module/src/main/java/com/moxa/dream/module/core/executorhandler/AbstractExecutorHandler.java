package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.core.statementhandler.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractExecutorHandler implements ExecutorHandler {
    protected StatementHandler statementHandler;
    private Connection connection;

    public AbstractExecutorHandler(StatementHandler statementHandler, Connection connection) throws SQLException {
        this.statementHandler = statementHandler;
        this.connection = connection;
    }

    @Override
    public Object execute(MappedStatement mappedStatement) throws SQLException {
        return statementHandler.doUpdate(connection, mappedStatement);
    }
}
