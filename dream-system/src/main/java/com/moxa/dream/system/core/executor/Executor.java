package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.Session;

import java.sql.SQLException;
import java.util.List;

public interface Executor {

    Object query(MappedStatement mappedStatement, Session session) throws SQLException;

    Object update(MappedStatement mappedStatement, Session session) throws SQLException;

    Object insert(MappedStatement mappedStatement, Session session) throws SQLException;

    Object delete(MappedStatement mappedStatement, Session session) throws SQLException;

    Object batch(List<MappedStatement> mappedStatements, Session session) throws SQLException;

    void commit();

    void rollback();

    void close();

    boolean isAutoCommit();
}
