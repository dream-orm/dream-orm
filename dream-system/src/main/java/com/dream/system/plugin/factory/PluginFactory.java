package com.dream.system.plugin.factory;

import com.dream.system.plugin.interceptor.Interceptor;

/**
 * 代理工厂类，不建议用，反射牺牲性能，建议采用com.dream.system.inject.Inject
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
