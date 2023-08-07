package com.dream.solon.transaction;

import com.dream.system.transaction.Transaction;
import org.noear.solon.data.tran.TranUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SolonTransaction implements Transaction {

    private DataSource dataSource;
    private Connection connection;
    private boolean isConnectionTransactional;
    private boolean autoCommit;

    public SolonTransaction(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            openConnection();
        }
        return connection;
    }

    @Override
    public void commit() {
        if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
            try {
                this.connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void rollback() {
        if (this.connection != null && !this.isConnectionTransactional && !this.autoCommit) {
            try {
                this.connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        if (connection != null && !TranUtils.inTrans()) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isAutoCommit() {
        return autoCommit;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) {

    }

    protected void openConnection() throws SQLException {
        this.connection = TranUtils.getConnection(dataSource);
        this.autoCommit = this.connection.getAutoCommit();
        this.isConnectionTransactional = TranUtils.inTrans();
    }
}
