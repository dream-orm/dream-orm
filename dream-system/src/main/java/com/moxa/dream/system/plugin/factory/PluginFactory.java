package com.moxa.dream.system.plugin.factory;

import com.moxa.dream.system.plugin.interceptor.Interceptor;

public interface PluginFactory {

    void interceptors(Interceptor... interceptors);

    <T> T plugin(T target);

    <T extends Interceptor> T getInterceptor(Class<T> interceptor);
}
