package com.moxa.dream.module.datasource;

import com.moxa.dream.module.mapped.MappedStatement;

import javax.sql.DataSource;
import java.util.Properties;

public interface DataSourceFactory {

    void setProperties(Properties properties);

    DataSource getDataSource(MappedStatement mappedStatement);

}
