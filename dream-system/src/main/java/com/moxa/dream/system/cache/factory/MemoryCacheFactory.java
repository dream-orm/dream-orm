package com.moxa.dream.system.cache.factory;

import com.moxa.dream.system.cache.Cache;
import com.moxa.dream.system.cache.MemoryCache;

import java.util.Properties;

public class MemoryCacheFactory extends AbstractCacheFactory {
    public static final String ENABLED = "enabled";
    public static final String LIMIT = "limit";
    public static final String RATE = "rate";
    private boolean disabled;
    private int limit = 100;
    private double rate = 0.25;

    @Override
    public void setProperties(Properties properties) {
        if (properties != null) {
            disabled = String.valueOf(false).equals(properties.get(ENABLED));
            Object limitObject = properties.get(LIMIT);
            Object rateObject = properties.get(RATE);
            if (limitObject != null) {
                limit = Integer.valueOf(limitObject.toString());
            }
            if (rateObject != null) {
                rate = Double.valueOf(rateObject.toString());
            }
        }
    }

    @Override
    public Cache createCache() {
        if (disabled)
            return null;
        else
            return new MemoryCache(limit, rate);
    }
}