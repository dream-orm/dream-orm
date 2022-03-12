package com.moxa.dream.module.cache.factory;


import com.moxa.dream.module.cache.Cache;

import java.util.Properties;

public interface CacheFactory {

    void setProperties(Properties properties);

    Cache getCache();
}
