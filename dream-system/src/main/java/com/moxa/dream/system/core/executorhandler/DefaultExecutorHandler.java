package com.moxa.dream.system.core.executorhandler;

import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.core.resultsethandler.DefaultResultSetHandler;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DefaultExecutorHandler implements ExecutorHandler {
    private final ResultSetHandler resultSetHandler;

    public DefaultExecutorHandler() {
        this.resultSetHandler = getResultSetHandler();
    }


    @Override
    public Object query(StatementHandler statementHandler, Connection connection, MappedStatement mappedStatement, Executor executor) throws SQLException {
        statementHandler.prepare(connection, mappedStatement, Statement.NO_GENERATED_KEYS);
        ResultSet resultSet = statementHandler.executeQuery(mappedStatement);
        return resultSetHandler.result(resultSet, mappedStatement, executor);
    }

    @Override
    public Object update(StatementHandler statementHandler, Connection connection, MappedStatement mappedStatement, Executor executor) throws SQLException {
        statementHandler.prepare(connection, mappedStatement, Statement.NO_GENERATED_KEYS);
        return statementHandler.executeUpdate(mappedStatement);
    }

    @Override
    public Object insert(StatementHandler statementHandler, Connection connection, MappedStatement mappedStatement, Executor executor) throws SQLException {
        statementHandler.prepare(connection, mappedStatement, Statement.NO_GENERATED_KEYS);
        return statementHandler.executeUpdate(mappedStatement);
    }

    @Override
    public Object delete(StatementHandler statementHandler, Connection connection, MappedStatement mappedStatement, Executor executor) throws SQLException {
        statementHandler.prepare(connection, mappedStatement, Statement.NO_GENERATED_KEYS);
        return statementHandler.executeUpdate(mappedStatement);
    }

    protected ResultSetHandler getResultSetHandler() {
        return new DefaultResultSetHandler();
    }
}
