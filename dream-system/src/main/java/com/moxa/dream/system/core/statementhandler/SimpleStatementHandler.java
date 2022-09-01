package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleStatementHandler implements StatementHandler {
    private Statement statement;

    @Override
    public void prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        statement = connection.createStatement();
    }

    protected void doTimeOut(MappedStatement mappedStatement) throws SQLException {
        int timeOut = mappedStatement.getTimeOut();
        if (timeOut != 0) {
            statement.setQueryTimeout(timeOut);
        }
    }

    @Override
    public ResultSet executeQuery(MappedStatement mappedStatement) throws SQLException {
        doTimeOut(mappedStatement);
        return statement.executeQuery(mappedStatement.getSql());
    }

    @Override
    public int executeUpdate(MappedStatement mappedStatement) throws SQLException {
        return statement.executeUpdate(mappedStatement.getSql());
    }

    @Override
    public void addBatch(MappedStatement mappedStatement) throws SQLException {
        statement.addBatch(mappedStatement.getSql());
    }

    @Override
    public void flushStatement(boolean rollback) {
        if (!rollback) {
            try {
                statement.executeBatch();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    @Override
    public void close() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
                statement = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
