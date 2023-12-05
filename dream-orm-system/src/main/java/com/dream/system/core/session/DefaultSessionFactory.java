package com.dream.system.core.session;

import com.dream.system.cache.Cache;
import com.dream.system.cache.CacheFactory;
import com.dream.system.config.Configuration;
import com.dream.system.core.executor.*;
import com.dream.system.datasource.DataSourceFactory;
import com.dream.system.transaction.Transaction;

import javax.sql.DataSource;


public class DefaultSessionFactory implements SessionFactory {
    protected Configuration configuration;
    protected DataSource dataSource;
    protected Cache cache;

    public DefaultSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
        dataSource = dataSourceFactory.getDataSource();
        CacheFactory cacheFactory = configuration.getCacheFactory();
        if (cacheFactory != null) {
            cache = cacheFactory.getCache();
        }
    }

    @Override
    public Session openSession(boolean autoCommit) {
        Transaction transaction = configuration.getTransactionFactory().getTransaction(dataSource);
        transaction.setAutoCommit(autoCommit);
        Executor executor = new JdbcExecutor(transaction, configuration.getStatementHandler(), configuration.getResultSetHandler());
        executor = new ActionExecutor(executor);
        if (this.cache != null) {
            executor = new CacheExecutor(executor, this.cache);
        }
        executor = new ListenerExecutor(executor, configuration.getListenerFactory());
        return openSession(executor);
    }

    @Override
    public Session openSession(Executor executor) {
        return new DefaultSession(configuration, executor);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
