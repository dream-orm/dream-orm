package com.dream.system.core.executor;

import com.dream.system.cache.Cache;
import com.dream.system.config.MappedStatement;

public class CacheExecutor extends AbstractCacheExecutor {
    protected Cache cache;

    public CacheExecutor(Executor executor, Cache cache) {
        super(executor);
        this.cache = cache;
    }

    @Override
    public void clear() {
        cache.clear();
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

