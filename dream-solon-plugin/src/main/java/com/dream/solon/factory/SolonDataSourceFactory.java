package com.dream.solon.factory;

import com.dream.system.datasource.DataSourceFactory;

import javax.sql.DataSource;

public class SolonDataSourceFactory implements DataSourceFactory {
    private DataSource dataSource;

    public SolonDataSourceFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}