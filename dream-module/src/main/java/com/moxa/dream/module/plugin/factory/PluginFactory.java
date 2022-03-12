package com.moxa.dream.module.plugin.factory;

import com.moxa.dream.module.plugin.interceptor.Interceptor;

public interface PluginFactory {

    void interceptor(Interceptor[] interceptors);

    Object plugin(Object target);
}
