package com.dream.drive.factory;

import com.dream.system.datasource.DataSourceFactory;

import javax.sql.DataSource;

public class DriveDataSourceFactory implements DataSourceFactory {
    private DataSource dataSource;

    public DriveDataSourceFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
