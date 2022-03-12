package com.moxa.dream.module.engine.transaction;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public interface Transaction {

    void setDataSource(DataSource dataSource);

    Connection getConnection() throws SQLException;

    void commit();

    void rollback();

    void close();

    boolean isAutoCommit();

    void setAutoCommit(boolean autoCommit);
}
