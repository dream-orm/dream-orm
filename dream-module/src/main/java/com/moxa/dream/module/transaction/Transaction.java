package com.moxa.dream.module.transaction;

import com.moxa.dream.module.mapped.MappedStatement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public interface Transaction {

    void setDataSource(DataSource dataSource);

    Connection getConnection(MappedStatement mappedStatement) throws SQLException;

    void commit() throws SQLException;

    void rollback() throws SQLException;

    void close() throws SQLException;

    boolean isAutoCommit();

    void setAutoCommit(boolean autoCommit);
}
