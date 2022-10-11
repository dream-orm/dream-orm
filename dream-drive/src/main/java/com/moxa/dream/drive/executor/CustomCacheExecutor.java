package com.moxa.dream.drive.executor;

import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.core.session.SessionFactory;

public class CustomCacheExecutor extends CacheExecutor {
    private final Cache cache;

    public CustomCacheExecutor(Cache cache, Executor executor, SessionFactory sessionFactory) {
        super(executor, sessionFactory);
        this.cache = cache;
    }

    @Override
    protected void clear() {
    }

    @Override
    protected Object queryFromCache(MappedStatement mappedStatement) {
        return cache.get(mappedStatement);
    }

    @Override
    protected void storeObject(MappedStatement mappedStatement, Object value) {
        cache.put(mappedStatement, value);
    }

    @Override
    protected void clearObject(MappedStatement mappedStatement) {
        cache.remove(mappedStatement);
    }
}

