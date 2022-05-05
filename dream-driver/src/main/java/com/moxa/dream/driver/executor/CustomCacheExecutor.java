package com.moxa.dream.driver.executor;

import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.core.executor.CacheExecutor;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapped.MappedStatement;

public class CustomCacheExecutor extends CacheExecutor {
    private final Cache cache;

    public CustomCacheExecutor(Cache cache, Executor executor) {
        super(executor);
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

