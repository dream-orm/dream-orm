package com.moxa.dream.drive.executor;

import com.moxa.dream.system.cache.CacheKey;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.core.session.SessionFactory;

import java.util.HashMap;
import java.util.Map;

public class SessionCacheExecutor extends CacheExecutor {
    private final Map<CacheKey, Object> cacheMap = new HashMap<>();

    public SessionCacheExecutor(Executor executor, SessionFactory sessionFactory) {
        super(executor, sessionFactory);
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
