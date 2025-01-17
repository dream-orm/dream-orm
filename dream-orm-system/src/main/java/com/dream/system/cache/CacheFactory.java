package com.dream.system.cache;


/**
 * 缓存工厂创建类
 */
public interface CacheFactory {

    /**
     * 是否开启session cache
     *
     * @return
     */
    default boolean isSessionCache() {
        return true;
    }

    /**
     * 获取缓存处理类
     *
     * @return
     */
    Cache getCache();
}
