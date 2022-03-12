package com.moxa.dream.module.engine.cache;


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
