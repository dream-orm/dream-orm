package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.config.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
    public ResultSet executeQuery(Statement statement, MappedStatement mappedStatement) throws SQLException {
        doTimeOut(statement, mappedStatement);
        return statement.executeQuery(mappedStatement.getSql());
    }

    @Override
    public Object executeUpdate(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statement.executeUpdate(mappedStatement.getSql());
    }

    @Override
    public Object executeInsert(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statement.executeUpdate(mappedStatement.getSql());
    }

    @Override
    public Object executeDelete(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statement.executeUpdate(mappedStatement.getSql());
    }

    @Override
    public Object executeBatch(Statement statement, List<MappedStatement> mappedStatements) throws SQLException {
        for (MappedStatement mappedStatement : mappedStatements) {
            statement.addBatch(mappedStatement.getSql());
        }
        int[] result = statement.executeBatch();
        statement.clearBatch();
        return result;
    }
}
