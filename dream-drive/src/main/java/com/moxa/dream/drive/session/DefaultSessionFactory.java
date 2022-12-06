package com.moxa.dream.drive.session;

import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.CacheFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.*;
import com.moxa.dream.system.core.resultsethandler.DefaultResultSetHandler;
import com.moxa.dream.system.core.resultsethandler.ResultSetHandler;
import com.moxa.dream.system.core.session.DefaultSession;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.core.session.SessionFactory;
import com.moxa.dream.system.core.statementhandler.PrepareStatementHandler;
import com.moxa.dream.system.core.statementhandler.StatementHandler;
import com.moxa.dream.system.datasource.DataSourceFactory;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.system.transaction.Transaction;
import com.moxa.dream.system.transaction.factory.TransactionFactory;

import javax.sql.DataSource;


public class DefaultSessionFactory implements SessionFactory {
    protected Configuration configuration;
    protected PluginFactory pluginFactory;
    protected TransactionFactory transactionFactory;
    protected DataSource dataSource;
    protected StatementHandler statementHandler;
    protected ResultSetHandler resultSetHandler;
    protected Cache cache;

    public DefaultSessionFactory(Configuration configuration) {
        this(configuration, new PrepareStatementHandler(), new DefaultResultSetHandler());
    }

    public DefaultSessionFactory(Configuration configuration, StatementHandler statementHandler, ResultSetHandler resultSetHandler) {
        this.configuration = configuration;
        this.statementHandler = statementHandler;
        this.resultSetHandler = resultSetHandler;
        DataSourceFactory dataSourceFactory = configuration.getDataSourceFactory();
        transactionFactory = configuration.getTransactionFactory();
        dataSource = dataSourceFactory.getDataSource();
        pluginFactory = configuration.getPluginFactory();
        CacheFactory cacheFactory = configuration.getCacheFactory();
        if (cacheFactory != null) {
            cache = cacheFactory.getCache();
        }
    }

    @Override
    public Session openSession(boolean autoCommit) {
        Transaction transaction = transactionFactory.getTransaction(dataSource);
        transaction.setAutoCommit(autoCommit);
        Executor executor = new JdbcExecutor(transaction, statementHandler, resultSetHandler);
        if (this.cache != null) {
            executor = new CacheExecutor(executor, this.cache);
        }
        executor = new ListenerExecutor(executor, configuration.getListenerFactory());
        executor = new ActionExecutor(executor);
        return openSession(executor);
    }

    @Override
    public Session openSession(Executor executor) {
        Session session = new DefaultSession(configuration, executor);
        if (pluginFactory != null) {
            session = (Session) pluginFactory.plugin(session);
        }
        return session;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
