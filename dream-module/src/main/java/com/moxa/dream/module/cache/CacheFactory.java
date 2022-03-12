package com.moxa.dream.module.cache;


import java.util.Properties;

public interface CacheFactory {

    void setProperties(Properties properties);

    Cache getCache();
}
