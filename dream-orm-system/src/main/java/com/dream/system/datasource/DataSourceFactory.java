package com.dream.system.datasource;

import javax.sql.DataSource;

/**
 * 数据源创建工厂
 */
public interface DataSourceFactory {

    /**
     * 数据源
     *
     * @return
     */
    DataSource getDataSource();

}
