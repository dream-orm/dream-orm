package com.moxa.dream.system.cache;


import com.moxa.dream.system.cache.Cache;

import java.util.Properties;

public interface CacheFactory {

    default void setProperties(Properties properties){

    }

    Cache getCache();
}
