package com.dream.mate.share.datasource;


import com.dream.mate.share.connection.ShareConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class ShareDataSource extends AbstractDataSource {
    protected String primary;
    protected Map<String, DataSource> dataSourceMap;

    public ShareDataSource(Map<String, DataSource> dataSourceMap) {
        this("master", dataSourceMap);
    }

    public ShareDataSource(String primary, Map<String, DataSource> dataSourceMap) {
        this.primary = primary;
        this.dataSourceMap = dataSourceMap;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ShareConnection(this);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }

    public Connection getConnection(String dataSourceName) throws SQLException {
        DataSource dataSource = dataSourceMap.get(dataSourceName);
        if (dataSource == null) {
            throw new SQLException("数据库" + dataSourceName + "不存在");
        }
        return dataSource.getConnection();
    }

    public String getPrimary() {
        return primary;
    }
}
