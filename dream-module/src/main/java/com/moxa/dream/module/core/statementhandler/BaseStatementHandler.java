package com.moxa.dream.module.core.statementhandler;

import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseStatementHandler implements StatementHandler {
    protected Statement statement;

    @Override
    public ResultSet doQuery(Connection connection, MappedStatement mappedStatement) throws SQLException {
        statement = doPrepare(connection, mappedStatement);
        return executeQuery(statement, mappedStatement);
    }

    @Override
    public int doUpdate(Connection connection, MappedStatement mappedStatement) throws SQLException {
        statement = doPrepare(connection, mappedStatement);
        return executeUpdate(statement, mappedStatement);
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

    protected Statement doPrepare(Connection connection, MappedStatement mappedStatement) throws SQLException {
        Statement statement = prepare(connection, mappedStatement);
        parameter(statement, mappedStatement);
        setQueryTimeOut(statement, mappedStatement);
        return statement;
    }

    protected abstract Statement prepare(Connection connection, MappedStatement mappedStatement) throws SQLException;

    protected abstract void parameter(Statement statement, MappedStatement mappedStatement) throws SQLException;

    protected abstract ResultSet executeQuery(Statement statement, MappedStatement mappedStatement) throws SQLException;

    protected abstract int executeUpdate(Statement statement, MappedStatement mappedStatement) throws SQLException;

    protected abstract int addBatch(Statement statement, MappedStatement mappedStatement) throws SQLException;

    protected void setQueryTimeOut(Statement statement, MappedStatement mappedStatement) throws SQLException {
        Integer timeOut = mappedStatement.getTimeOut();
        if (timeOut != null)
            statement.setQueryTimeout(timeOut);
    }
}
