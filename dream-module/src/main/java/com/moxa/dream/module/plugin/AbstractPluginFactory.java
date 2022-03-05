package com.moxa.dream.module.plugin;

import com.moxa.dream.util.common.ObjectUtil;

public abstract class AbstractPluginFactory implements PluginFactory {
    protected Interceptor[] interceptors;

    public void interceptor(Interceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    public Object plugin(Object target) {
        Object origin = target;
        return plugin(interceptors, origin, plugin(getDefaultInterceptorList(), origin, target));
    }

    private Object plugin(Interceptor[] interceptors, Object origin, Object target) {
        if (!ObjectUtil.isNull(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                target = plugin(origin, target, interceptor);
            }
        }
        return target;
    }

    protected abstract Interceptor[] getDefaultInterceptorList();

    protected abstract Object plugin(Object origin, Object target, Interceptor interceptor);
}
