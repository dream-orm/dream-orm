package com.moxa.dream.module.plugin;

public interface PluginFactory {

    void interceptor(Interceptor[] interceptors);

    Object plugin(Object target);
}
