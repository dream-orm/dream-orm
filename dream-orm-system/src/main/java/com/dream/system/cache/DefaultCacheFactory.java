package com.dream.system.cache;


public class DefaultCacheFactory implements CacheFactory {
    private Cache cache;
    private boolean sessionCache;
    @Override
    public Cache getCache() {
        return cache;
    }

    @Override
    public boolean isSessionCache() {
        return sessionCache;
    }

    public void setSessionCache(boolean sessionCache) {
        this.sessionCache = sessionCache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
