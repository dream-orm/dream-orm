package com.dream.boot.factory;

import com.dream.system.datasource.DataSourceFactory;

import javax.sql.DataSource;

public class SpringDataSourceFactory implements DataSourceFactory {
    private DataSource dataSource;

    public SpringDataSourceFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
