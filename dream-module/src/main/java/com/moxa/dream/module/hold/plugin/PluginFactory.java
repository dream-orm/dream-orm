package com.moxa.dream.module.hold.plugin;

public interface PluginFactory {

    void interceptor(Interceptor[] interceptors);

    Object plugin(Object target);
}
