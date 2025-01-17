package com.dream.system.cache;


public class DefaultCacheFactory implements CacheFactory {
    private Cache cache;
    private boolean sessionCache = true;

    @Override
    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public boolean isSessionCache() {
        return sessionCache;
    }

    public void setSessionCache(boolean sessionCache) {
        this.sessionCache = sessionCache;
    }
}
