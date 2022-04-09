package com.moxa.dream.system.cache.factory;


import com.moxa.dream.system.cache.Cache;

public abstract class AbstractCacheFactory implements CacheFactory {

    private Cache cache;

    @Override
    public Cache getCache() {
        if (cache == null) {
            synchronized (this) {
                if (cache == null) {
                    cache = createCache();
                }
            }
        }
        return cache;
    }

    protected abstract Cache createCache();
}
