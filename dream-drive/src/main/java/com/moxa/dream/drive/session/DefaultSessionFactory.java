package com.moxa.dream.drive.session;

import com.moxa.dream.drive.executor.CustomCacheExecutor;
import com.moxa.dream.drive.executor.SessionCacheExecutor;
import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.factory.CacheFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.core.executor.JdbcExecutor;
import com.moxa.dream.system.datasource.DataSourceFactory;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.system.transaction.factory.TransactionFactory;

import javax.sql.DataSource;


public class DefaultSessionFactory implements SessionFactory {
    private final Configuration configuration;
    private final Control control;
    private PluginFactory pluginFactory;
    private TransactionFactory transactionFactory;
    private DataSource dataSource;
    private Cache cache;

    public DefaultSessionFactory(Configuration configuration) {
        this.configuration = configuration;
        DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
        transactionFactory = configuration.getTransactionFactory();
        dataSource = dataSourceFactory.getDataSource();
        pluginFactory = configuration.getPluginFactory();
        CacheFactory cacheFactory = configuration.getCacheFactory();
        if (cacheFactory != null) {
            cache = cacheFactory.getCache();
        }
        control = new Control
                .Builder()
                .autoCommit(false)
                .cache(true)
                .build();
    }

    @Override
    public Session openSession() {
        return openSession(control);
    }

    @Override
    public Session openSession(Control control) {
        Transaction transaction = transactionFactory.getTransaction(dataSource);
        transaction.setAutoCommit(control.isAutoCommit());
        Executor executor = new JdbcExecutor(configuration, transaction);

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
    public Session openSession(Executor executor) {
        return new DefaultSession(configuration, executor);
    }
}
