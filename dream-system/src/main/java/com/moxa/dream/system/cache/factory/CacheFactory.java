package com.moxa.dream.system.cache.factory;


import com.moxa.dream.system.cache.Cache;

import java.util.Properties;

public interface CacheFactory {

    void setProperties(Properties properties);

    Cache getCache();
}
