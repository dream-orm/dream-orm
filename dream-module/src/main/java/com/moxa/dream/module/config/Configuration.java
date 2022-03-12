package com.moxa.dream.module.config;

import com.moxa.dream.module.cache.factory.CacheFactory;
import com.moxa.dream.module.datasource.DataSourceFactory;
import com.moxa.dream.module.dialect.DialectFactory;
import com.moxa.dream.module.mapper.factory.MapperFactory;
import com.moxa.dream.module.plugin.factory.PluginFactory;
import com.moxa.dream.module.table.factory.TableFactory;
import com.moxa.dream.module.transaction.Transaction;
import com.moxa.dream.module.transaction.factory.TransactionFactory;
import com.moxa.dream.module.typehandler.factory.TypeHandlerFactory;

import javax.sql.DataSource;

public class Configuration {
    private MapperFactory mapperFactory;
    private TableFactory tableFactory;
    private CacheFactory cacheFactory;
    private TypeHandlerFactory typeHandlerFactory;
    private DialectFactory dialectFactory;
    private PluginFactory pluginFactory;
    private TransactionFactory transactionFactory;
    private DataSourceFactory dataSourceFactory;


    public void addMapper(Class type) {
        mapperFactory.addMapper(this, type);
    }

    public Transaction getTransaction(boolean autoCommit) {
        DataSource dataSource = dataSourceFactory.getDataSource();
        Transaction transaction = transactionFactory.getTransaction();
        transaction.setDataSource(dataSource);
        transaction.setAutoCommit(autoCommit);
        return transaction;
    }

    public MapperFactory getMapperFactory() {
        return mapperFactory;
    }

    public void setMapperFactory(MapperFactory mapperFactory) {
        this.mapperFactory = mapperFactory;
    }

    public TableFactory getTableFactory() {
        return tableFactory;
    }

    public void setTableFactory(TableFactory tableFactory) {
        this.tableFactory = tableFactory;
    }

    public CacheFactory getCacheFactory() {
        return cacheFactory;
    }

    public void setCacheFactory(CacheFactory cacheFactory) {
        this.cacheFactory = cacheFactory;
    }

    public TypeHandlerFactory getTypeHandlerFactory() {
        return typeHandlerFactory;
    }

    public void setTypeHandlerFactory(TypeHandlerFactory typeHandlerFactory) {
        this.typeHandlerFactory = typeHandlerFactory;
    }

    public DialectFactory getDialectFactory() {
        return dialectFactory;
    }

    public void setDialectFactory(DialectFactory dialectFactory) {
        this.dialectFactory = dialectFactory;
    }

    public PluginFactory getPluginFactory() {
        return pluginFactory;
    }

    public void setPluginFactory(PluginFactory pluginFactory) {
        this.pluginFactory = pluginFactory;
    }

    public TransactionFactory getTransactionFactory() {
        return transactionFactory;
    }

    public void setTransactionFactory(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

}
