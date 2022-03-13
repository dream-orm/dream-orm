package com.moxa.dream.module.plugin.factory;

import com.moxa.dream.module.plugin.interceptor.Interceptor;
import com.moxa.dream.util.common.ObjectUtil;

public abstract class AbstractPluginFactory implements PluginFactory {
    protected Interceptor[] interceptors;

    public void interceptor(Interceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    public Object plugin(Object target) {
        Object origin = target;
        return plugin(interceptors, origin, plugin(getDefaultInterceptors(), origin, target));
    }

    private Object plugin(Interceptor[] interceptors, Object origin, Object target) {
        if (!ObjectUtil.isNull(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                target = plugin(origin, target, interceptor);
            }
        }
        return target;
    }

    protected abstract Interceptor[] getDefaultInterceptors();

    protected abstract Object plugin(Object origin, Object target, Interceptor interceptor);
}
