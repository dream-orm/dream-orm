package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;

import java.sql.SQLException;

public interface Executor {

    Object query(MappedStatement mappedStatement, Session session) throws SQLException;

    Object update(MappedStatement mappedStatement, Session session) throws SQLException;

    Object insert(MappedStatement mappedStatement, Session session) throws SQLException;

    Object delete(MappedStatement mappedStatement, Session session) throws SQLException;

    Object batch(BatchMappedStatement batchMappedStatement, Session session) throws SQLException;

    Object truncate(MappedStatement mappedStatement, Session session) throws SQLException;

    Object drop(MappedStatement mappedStatement, Session session) throws SQLException;

    void commit();

    void rollback();

    void close();

    boolean isAutoCommit();
}
