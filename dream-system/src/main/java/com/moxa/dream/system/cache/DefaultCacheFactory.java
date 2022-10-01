package com.moxa.dream.system.cache;


public class DefaultCacheFactory implements CacheFactory {
    private Cache cache;

    public void setCache(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Cache getCache() {
        if(cache==null){
            synchronized (this){
                if(cache==null){
                    cache=new MemoryCache(100,0.25);
                }
            }
        }
        return cache;
    }
}
