package com.moxa.dream.share.connection;

import com.moxa.dream.share.datasource.ShareDataSource;
import com.moxa.dream.share.holder.DataSourceHolder;
import com.moxa.dream.util.common.ObjectUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.*;

public class ShareConnection extends AbstractConnection {
    private Map<String, Connection> connectionMap = new LinkedHashMap<>();
    private List<Initialization> initializationList = new ArrayList<>(4);
    private ShareDataSource shareDataSource;
    private boolean autoCommit = true;
    private boolean close = false;
    private boolean readOnly = false;
    private int level;

    public ShareConnection(ShareDataSource shareDataSource) {
        this.shareDataSource = shareDataSource;
    }

    @Override
    protected Connection getConnection() throws SQLException {
        String dataSourceName = getDataSourceName();
        Connection connection = connectionMap.get(dataSourceName);
        if (connection == null) {
            connection = shareDataSource.getConnection(dataSourceName);
            if (!ObjectUtil.isNull(initializationList)) {
                for (Initialization initialization : initializationList) {
                    initialization.doInit(connection);
                }
            }
            connectionMap.put(dataSourceName, connection);
        }
        return connection;
    }

    @Override
    public void commit() throws SQLException {
        Collection<Connection> connections = connectionMap.values();
        Connection[] connectionArray = connections.toArray(new Connection[0]);
        for (int i = connectionArray.length - 1; i >= 0; i--) {
            connectionArray[i].commit();
        }
    }

    @Override
    public void rollback() throws SQLException {
        Collection<Connection> connections = connectionMap.values();
        Connection[] connectionArray = connections.toArray(new Connection[0]);
        for (int i = connectionArray.length - 1; i >= 0; i--) {
            connectionArray[i].rollback();
        }
    }

    @Override
    public void close() throws SQLException {
        Collection<Connection> connections = connectionMap.values();
        Connection[] connectionArray = connections.toArray(new Connection[0]);
        for (int i = connectionArray.length - 1; i >= 0; i--) {
            connectionArray[i].close();
        }
        close = true;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return close;
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        String dataSourceName = getDataSourceName();
        Connection connection = connectionMap.get(dataSourceName);
        if (connection != null) {
            return connection.getMetaData();
        } else {
            return null;
        }
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return readOnly;
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        initializationList.add(connection -> connection.setReadOnly(readOnly));
        this.readOnly = readOnly;
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return autoCommit;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        initializationList.add(connection -> connection.setAutoCommit(autoCommit));
        this.autoCommit = autoCommit;
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return level;
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        initializationList.add(connection -> connection.setTransactionIsolation(level));
        this.level = level;
    }

    protected String getDataSourceName() {
        String dataSourceName = DataSourceHolder.get();
        if (dataSourceName == null) {
            dataSourceName = shareDataSource.getPrimary();
        }
        return dataSourceName;
    }

    interface Initialization {
        void doInit(Connection connection) throws SQLException;
    }
}
