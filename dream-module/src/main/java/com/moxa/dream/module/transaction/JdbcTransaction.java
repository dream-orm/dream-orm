package com.moxa.dream.module.transaction;

import com.moxa.dream.module.mapped.MappedStatement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcTransaction implements Transaction {

    protected Connection connection;
    protected boolean autoCommit;
    private DataSource dataSource;

    @Override
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection(MappedStatement mappedStatement) throws SQLException {
        if (connection == null) {
            openConnection(mappedStatement);
        }
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        if (connection != null && !connection.getAutoCommit()) {
            connection.rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            resetAutoCommit();
            connection.close();
        }
    }

    @Override
    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    protected void setDesiredAutoCommit(boolean desiredAutoCommit) throws SQLException {
        if (connection.getAutoCommit() != desiredAutoCommit) {
            connection.setAutoCommit(desiredAutoCommit);
        }
    }

    protected void resetAutoCommit() throws SQLException {
        if (!connection.getAutoCommit()) {
            connection.setAutoCommit(true);
        }
    }

    protected void openConnection(MappedStatement mappedStatement) throws SQLException {
        connection = dataSource.getConnection();
        setDesiredAutoCommit(autoCommit);
    }
}
