package com.moxa.dream.module.engine.statementhandler;

import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleStatementHandler extends BaseStatementHandler {

    @Override
    protected Statement prepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        return connection.createStatement();
    }

    @Override
    protected void parameter(Statement statement, MappedStatement mappedStatement) throws SQLException {

    }

    @Override
    protected ResultSet executeQuery(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statement.executeQuery(mappedStatement.getSql());
    }

    @Override
    protected int executeUpdate(Statement statement, MappedStatement mappedStatement) throws SQLException {
        return statement.executeUpdate(mappedStatement.getSql());
    }

    @Override
    protected int addBatch(Statement statement, MappedStatement mappedStatement) throws SQLException {
        statement.addBatch(mappedStatement.getSql());
        return 0;
    }

    @Override
    public int[] flushStatement(boolean rollback) {
        return null;
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