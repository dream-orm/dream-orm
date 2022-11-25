package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleStatementHandler implements StatementHandler<Statement> {
    @Override
    public Statement prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        return connection.createStatement();
    }

    protected void doTimeOut(Statement statement, MappedStatement mappedStatement) throws SQLException {
        int timeOut = mappedStatement.getTimeOut();
        if (timeOut != 0) {
            statement.setQueryTimeout(timeOut);
        }
    }

    @Override
    public ResultSet query(Statement statement, MappedStatement mappedStatement) throws SQLException {
        doTimeOut(statement, mappedStatement);
        return statement.executeQuery(mappedStatement.getSql());
    }

    @Override
    public Object update(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statement.executeUpdate(mappedStatement.getSql());
    }

    @Override
    public Object insert(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statement.executeUpdate(mappedStatement.getSql());
    }

    @Override
    public Object delete(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statement.executeUpdate(mappedStatement.getSql());
    }

    @Override
    public Object batch(Statement statement, BatchMappedStatement batchMappedStatement) throws SQLException {
        for (MappedStatement mappedStatement : batchMappedStatement.getMappedStatementList()) {
            statement.addBatch(mappedStatement.getSql());
        }
        int[] result = statement.executeBatch();
        statement.clearBatch();
        return result;
    }
}
