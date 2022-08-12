package com.moxa.dream.system.core.executorhandler;

import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.Connection;
import java.sql.SQLException;

public interface ExecutorHandler {
    Object query(StatementHandler statementHandler, Connection connection, MappedStatement mappedStatement, Executor executor) throws SQLException;

    Object update(StatementHandler statementHandler, Connection connection, MappedStatement mappedStatement, Executor executor) throws SQLException;

    Object insert(StatementHandler statementHandler, Connection connection, MappedStatement mappedStatement, Executor executor) throws SQLException;

    Object delete(StatementHandler statementHandler, Connection connection, MappedStatement mappedStatement, Executor executor) throws SQLException;
}
