package com.moxa.dream.engine.executor;

import com.moxa.dream.engine.result.ResultSetHandler;
import com.moxa.dream.engine.statement.StatementHandler;
import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.SQLException;

public interface Executor {

    Object query(MappedStatement mappedStatement) throws SQLException;

    Object update(MappedStatement mappedStatement) throws SQLException;

    Object insert(MappedStatement mappedStatement) throws SQLException;

    Object delete(MappedStatement mappedStatement) throws SQLException;

    StatementHandler getStatementHandler(MappedStatement mappedStatement);

    ResultSetHandler getResultSetHandler(MappedStatement mappedStatement);

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

    boolean isAutoCommit();
}
