package com.moxa.dream.system.cache;


public class DefaultCacheFactory implements CacheFactory {
    private Cache cache;

    @Override
    public Cache getCache() {
        return cache;
    }

    public void setCache(Cache cache) {
        this.cache = cache;
    }
}
