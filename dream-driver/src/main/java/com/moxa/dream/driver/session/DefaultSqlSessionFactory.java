package com.moxa.dream.driver.session;

import com.moxa.dream.driver.executor.CustomCacheExecutor;
import com.moxa.dream.driver.executor.SessionCacheExecutor;
import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.factory.CacheFactory;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.BatchExecutor;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.core.executor.JdbcExecutor;
import com.moxa.dream.system.plugin.factory.PluginFactory;
import com.moxa.dream.util.common.ObjectUtil;


public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        ObjectUtil.requireNonNull(configuration, "Property 'configuration' is required");
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return openSession(false);
    }

    @Override
    public SqlSession openSession(boolean autoCommit) {
        return openSession(autoCommit, false);
    }

    @Override
    public SqlSession openSession(boolean autoCommit, boolean batch) {
        return openSession(autoCommit, batch, true);
    }

    @Override
    public SqlSession openSession(boolean autoCommit, boolean batch, boolean enable) {
        CacheFactory cacheFactory = configuration.getCacheFactory();
        PluginFactory pluginFactory = configuration.getPluginFactory();
        Cache cache = null;
        if (cacheFactory != null)
            cache = cacheFactory.getCache();
        Executor executor;
        if (batch) {
            executor = new BatchExecutor(configuration, autoCommit);
        } else {
            executor = new JdbcExecutor(configuration, autoCommit);
        }

        if (cache != null) {
            executor = new CustomCacheExecutor(cache, executor);
        }
        if (enable) {
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
