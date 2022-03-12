package com.moxa.dream.module.engine.executor;

import com.moxa.dream.module.engine.result.ResultSetHandler;
import com.moxa.dream.module.engine.statement.StatementHandler;
import com.moxa.dream.module.hold.mapped.MappedStatement;

import java.sql.SQLException;

public interface Executor {

    Object query(MappedStatement mappedStatement) throws SQLException;

    Object update(MappedStatement mappedStatement) throws SQLException;

    Object insert(MappedStatement mappedStatement) throws SQLException;

    Object delete(MappedStatement mappedStatement) throws SQLException;

    StatementHandler getStatementHandler();

    ResultSetHandler getResultSetHandler();

    void commit();

    void rollback();

    void close();

    boolean isAutoCommit();
}
