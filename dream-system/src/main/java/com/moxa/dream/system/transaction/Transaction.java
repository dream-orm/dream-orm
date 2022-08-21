package com.moxa.dream.system.transaction;

import java.sql.Connection;
import java.sql.SQLException;

public interface Transaction {

    Connection getConnection() throws SQLException;

    void commit();

    void rollback();

    void close();

    boolean isAutoCommit();

    void setAutoCommit(boolean autoCommit);
}
