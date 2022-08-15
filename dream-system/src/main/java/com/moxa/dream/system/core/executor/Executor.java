package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.mapped.MappedStatement;

import java.sql.SQLException;
import java.sql.Statement;

public interface Executor {

    Object query(MappedStatement mappedStatement) throws SQLException;

    Object update(MappedStatement mappedStatement) throws SQLException;

    Object insert(MappedStatement mappedStatement) throws SQLException;

    Object delete(MappedStatement mappedStatement) throws SQLException;

    void commit();

    void rollback();

    void close();

    boolean isAutoCommit();

    Statement getStatement();
}
