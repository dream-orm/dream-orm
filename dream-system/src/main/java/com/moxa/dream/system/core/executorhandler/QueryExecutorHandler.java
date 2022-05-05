package com.moxa.dream.system.core.executorhandler;

import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutorHandler implements ExecutorHandler {
    private final StatementHandler statementHandler;
    private final ResultSetHandler resultSetHandler;
    private final Connection connection;

    public QueryExecutorHandler(StatementHandler statementHandler, ResultSetHandler resultSetHandler, Connection connection) {
        this.statementHandler = statementHandler;
        this.resultSetHandler = resultSetHandler;
        this.connection = connection;
    }

    @Override
    public Object execute(MappedStatement mappedStatement) throws SQLException {
        statementHandler.prepare(connection, mappedStatement, Statement.NO_GENERATED_KEYS);
        ResultSet resultSet = statementHandler.executeQuery(mappedStatement);
        return resultSetHandler.result(resultSet, mappedStatement);
    }
}
