package com.moxa.dream.system.cache.factory;

import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.MemoryCache;

import java.util.Properties;

public class MemoryCacheFactory implements CacheFactory {
    private final String ENABLE = "enable";
    private final String LIMIT = "limit";
    private final String RATE = "rate";
    private int limit = 100;
    private double rate = 0.25;
    private Cache cache = new MemoryCache(limit, rate);

    @Override
    public void setProperties(Properties properties) {
        String enable = String.valueOf(true);
        if (properties != null) {
            Object limitObject = properties.get(LIMIT);
            Object rateObject = properties.get(RATE);
            if (limitObject != null) {
                limit = Integer.valueOf(limitObject.toString());
            }
            if (rateObject != null) {
                rate = Double.valueOf(rateObject.toString());
            }
            enable = properties.getProperty(ENABLE);
        }
        if (!String.valueOf(false).equals(enable)) {
            cache = new MemoryCache(limit, rate);
        } else {
            cache = null;
        }
    }

    @Override
    public Cache getCache() {
        return cache;
    }
}
