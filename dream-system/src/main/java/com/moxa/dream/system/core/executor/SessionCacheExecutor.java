package com.moxa.dream.system.core.executor;

import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.MappedStatement;

import java.util.HashMap;
import java.util.Map;

public class SessionCacheExecutor extends AbstractCacheExecutor {
    private final Map<CacheKey, Object> cacheMap = new HashMap<>();

    public SessionCacheExecutor(Executor executor) {
        super(executor);
    }

    @Override
    public void clear() {
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

    @Override
    public void close() {
        super.close();
        clear();
    }
}
