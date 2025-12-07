package com.dream.system.core.executor;

import com.dream.system.cache.CacheKey;
import com.dream.system.config.MappedStatement;

import java.util.HashMap;
import java.util.Map;

public class SessionCacheExecutor extends AbstractCacheExecutor {
    private final Map<CacheKey, Object> cacheMap = new HashMap<>(4);

    public SessionCacheExecutor(Executor executor) {
        super(executor);
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
        cacheMap.clear();
    }

    @Override
    protected boolean cache(MappedStatement mappedStatement) {
        return mappedStatement.getUniqueKey() != null;
    }
}
