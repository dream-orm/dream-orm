package com.moxa.dream.driver.factory;

import com.moxa.dream.driver.interceptor.PageInterceptor;
import com.moxa.dream.module.hold.plugin.Interceptor;
import com.moxa.dream.module.hold.plugin.JavaPluginFactory;

public class DefaultPluginFactory extends JavaPluginFactory {
    @Override
    protected Interceptor[] getDefaultInterceptorList() {
        return new Interceptor[]{new PageInterceptor()};
    }
}
