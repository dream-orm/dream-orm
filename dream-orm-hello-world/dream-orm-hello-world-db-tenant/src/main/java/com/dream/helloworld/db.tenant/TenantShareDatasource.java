package com.dream.helloworld.db.tenant;

import com.dream.mate.share.datasource.AbstractDataSource;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TenantShareDatasource extends AbstractDataSource {
    private Map<String, DataSource> dataSourceMap = new HashMap<>();

    @Override
    public Connection getConnection() throws SQLException {
        String tenantId = TenantUtil.getTenantId();
        DataSource dataSource = dataSourceMap.get(tenantId);
        if (dataSource == null) {
            synchronized (this) {
                dataSource = dataSourceMap.get(tenantId);
                if (dataSource == null) {
                    //自己获取数据源操作
                    HikariDataSource hikariDataSource = new HikariDataSource();
                    hikariDataSource.setJdbcUrl("jdbc:mysql://192.168.0.242/d-open-sun");
                    hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
                    hikariDataSource.setPassword("BMW#Halu@1234%");
                    hikariDataSource.setUsername("root");
                    dataSource = hikariDataSource;
                    dataSourceMap.put(tenantId, dataSource);
                }
            }
        }
        return dataSource.getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getConnection();
    }
}
