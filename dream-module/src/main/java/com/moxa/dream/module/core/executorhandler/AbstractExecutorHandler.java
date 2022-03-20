package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.core.statementhandler.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractExecutorHandler implements ExecutorHandler {
    protected StatementHandler statementHandler;
    protected Connection connection;

    public AbstractExecutorHandler(StatementHandler statementHandler, Connection connection) {
        this.statementHandler = statementHandler;
        this.connection = connection;
    }

    @Override
    public Object execute(MappedStatement mappedStatement) throws SQLException {
        statementHandler.prepare(connection,mappedStatement, Statement.NO_GENERATED_KEYS);
        return statementHandler.executeUpdate(mappedStatement);
    }
}
