package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.session.SessionFactory;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface Executor {

    Object query(MappedStatement mappedStatement) throws SQLException;

    Object update(MappedStatement mappedStatement) throws SQLException;

    Object insert(MappedStatement mappedStatement) throws SQLException;

    Object delete(MappedStatement mappedStatement) throws SQLException;

    Object batch(List<MappedStatement> mappedStatements) throws SQLException;

    void commit();

    void rollback();

    void close();

    boolean isAutoCommit();

    SessionFactory getSessionFactory();
}
