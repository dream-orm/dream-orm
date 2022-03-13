package com.moxa.dream.module.core.executorhandler;

import com.moxa.dream.module.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.module.core.statementhandler.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryExecutorHandler implements ExecutorHandler {
    private StatementHandler statementHandler;
    private ResultSetHandler resultSetHandler;
    private Connection connection;

    public QueryExecutorHandler(StatementHandler statementHandler, ResultSetHandler resultSetHandler, Connection connection) throws SQLException {
        this.statementHandler = statementHandler;
        this.resultSetHandler = resultSetHandler;
        this.connection = connection;
    }

    @Override
    public Object execute(MappedStatement mappedStatement) throws SQLException {
        ResultSet resultSet = statementHandler.doQuery(connection, mappedStatement);
        return resultSetHandler.result(resultSet, mappedStatement);
    }
}
