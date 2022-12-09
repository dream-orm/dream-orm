package com.moxa.dream.system.plugin.factory;

import com.moxa.dream.system.plugin.interceptor.Interceptor;

public interface PluginFactory {

    void interceptor(Interceptor...interceptors);

    Object plugin(Object target);

    <T extends Interceptor> T getInterceptor(Class<T> interceptor);
}
