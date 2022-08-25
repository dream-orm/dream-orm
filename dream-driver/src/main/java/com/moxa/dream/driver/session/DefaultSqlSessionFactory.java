package com.moxa.dream.driver.session;

import com.moxa.dream.driver.executor.CustomCacheExecutor;
import com.moxa.dream.driver.executor.SessionCacheExecutor;
import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.factory.CacheFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.BatchExecutor;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.core.executor.JdbcExecutor;
import com.moxa.dream.system.datasource.DataSourceFactory;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.system.transaction.factory.TransactionFactory;

import javax.sql.DataSource;


public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final Configuration configuration;
    private PluginFactory pluginFactory;
    private Cache cache;
    private DataSource dataSource;
    private TransactionFactory transactionFactory;
    private final Control control;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        this.pluginFactory = configuration.getPluginFactory();
        this.transactionFactory = configuration.getTransactionFactory();
        DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
        this.dataSource = dataSourceFactory.getDataSource();
        CacheFactory cacheFactory = configuration.getCacheFactory();
        if (cacheFactory != null) {
            this.cache = cacheFactory.getCache();
        }
        control=new Control
                .Builder()
                .autoCommit(false)
                .batch(false)
                .cache(true)
                .build();
    }

    @Override
    public SqlSession openSession() {
        return openSession(control);
    }

    @Override
    public SqlSession openSession(Control control) {
        Transaction transaction = transactionFactory.getTransaction(dataSource);
        transaction.setAutoCommit(control.isAutoCommit());
        Executor executor;
        if (control.isBatch()) {
            executor = new BatchExecutor(configuration, transaction);
        } else {
            executor = new JdbcExecutor(configuration, transaction);
        }
        if (this.cache != null) {
            executor = new CustomCacheExecutor(this.cache, executor);
        }
        if (control.isCache()) {
            executor = new SessionCacheExecutor(executor);
        }
        if (pluginFactory != null)
            executor = (Executor) pluginFactory.plugin(executor);
        return openSession(executor);
    }

    @Override
    public SqlSession openSession(Executor executor) {
        return new DefaultSqlSession(configuration, executor);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
