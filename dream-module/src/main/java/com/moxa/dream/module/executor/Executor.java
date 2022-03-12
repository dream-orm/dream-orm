package com.moxa.dream.module.executor;

import com.moxa.dream.module.mapped.MappedStatement;
import com.moxa.dream.module.resultsethandler.ResultSetHandler;
import com.moxa.dream.module.statementhandler.StatementHandler;

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
