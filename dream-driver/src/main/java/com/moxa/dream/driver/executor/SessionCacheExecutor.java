package com.moxa.dream.driver.executor;

import com.moxa.dream.module.cache.CacheKey;
import com.moxa.dream.module.core.executor.CacheExecutor;
import com.moxa.dream.module.core.executor.Executor;
import com.moxa.dream.module.mapped.MappedStatement;

import java.util.HashMap;
import java.util.Map;

public class SessionCacheExecutor extends CacheExecutor {
    private Map<CacheKey, Object> cacheMap = new HashMap<>();

    public SessionCacheExecutor(Executor executor) {
        super(executor);
    }

    @Override
    protected void clear() {
        cacheMap.clear();
    }

    @Override
    protected Object queryFromCache(MappedStatement mappedStatement) {
        return cacheMap.get(mappedStatement.getUniqueKey());
    }

    @Override
    protected void storeObject(MappedStatement mappedStatement, Object value) {
        cacheMap.put(mappedStatement.getUniqueKey(), value);
    }

    @Override
    protected void clearObject(MappedStatement mappedStatement) {
        cacheMap.clear();
    }
}
