package com.dream.system.plugin.factory;

import com.dream.system.plugin.interceptor.Interceptor;

/**
 * 代理工厂类，反射实现，暂时未使用
 */
public interface PluginFactory {

    /**
     * 代理对象的选择器
     *
     * @param interceptors 代理对象的选择器
     */
    void interceptors(Interceptor... interceptors);

    /**
     * 代理对象
     *
     * @param target
     * @param <T>
     * @return
     */
    <T> T plugin(T target);
}
