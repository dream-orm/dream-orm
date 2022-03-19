package com.moxa.dream.module.core.executor;

import com.moxa.dream.module.mapped.MappedStatement;

import java.sql.SQLException;

public interface Executor {

    Object query(MappedStatement mappedStatement) throws SQLException;

    Object update(MappedStatement mappedStatement) throws SQLException;

    Object insert(MappedStatement mappedStatement) throws SQLException;

    Object delete(MappedStatement mappedStatement) throws SQLException;

    void commit();

    void rollback();

    void close();

    boolean isAutoCommit();
}
