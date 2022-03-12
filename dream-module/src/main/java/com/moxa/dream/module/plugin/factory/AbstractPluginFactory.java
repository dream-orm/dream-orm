package com.moxa.dream.module.plugin.factory;

import com.moxa.dream.module.plugin.interceptor.Interceptor;
import com.moxa.dream.module.plugin.interceptor.PageInterceptor;
import com.moxa.dream.util.common.ObjectUtil;

public abstract class AbstractPluginFactory implements PluginFactory {
    protected Interceptor[] interceptors;
    protected Interceptor[] defaultInterceptors = new Interceptor[]{new PageInterceptor()};

    public void interceptor(Interceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    public Object plugin(Object target) {
        Object origin = target;
        return plugin(interceptors, origin, plugin(defaultInterceptors, origin, target));
    }

    private Object plugin(Interceptor[] interceptors, Object origin, Object target) {
        if (!ObjectUtil.isNull(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                target = plugin(origin, target, interceptor);
            }
        }
        return target;
    }

    protected abstract Object plugin(Object origin, Object target, Interceptor interceptor);
}
