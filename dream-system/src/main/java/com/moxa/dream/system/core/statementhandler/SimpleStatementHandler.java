package com.moxa.dream.system.core.statementhandler;

import com.moxa.dream.system.config.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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
    public int[] executeBatch(List<MappedStatement> mappedStatements) throws SQLException {
        for (MappedStatement mappedStatement : mappedStatements) {
            statement.addBatch(mappedStatement.getSql());
        }
        int[] result = statement.executeBatch();
        statement.clearBatch();
        return result;
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
