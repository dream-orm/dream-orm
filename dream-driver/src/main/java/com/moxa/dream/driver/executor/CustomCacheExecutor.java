package com.moxa.dream.driver.executor;

import com.moxa.dream.module.engine.cache.Cache;
import com.moxa.dream.module.engine.executor.CacheExecutor;
import com.moxa.dream.module.engine.executor.Executor;
import com.moxa.dream.module.mapped.MappedStatement;

public class CustomCacheExecutor extends CacheExecutor {
    private Cache cache;

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

