package com.moxa.dream.system.plugin.factory;

import com.moxa.dream.system.plugin.interceptor.Interceptor;
import com.moxa.dream.util.common.ObjectUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPluginFactory implements PluginFactory {
    private Map<Class, Interceptor> interceptorMap = new HashMap<>();

    public void interceptor(Interceptor...interceptors) {
        interceptorMap.clear();
        for (Interceptor interceptor : interceptors) {
            interceptorMap.put(interceptor.getClass(), interceptor);
        }
    }

    public Object plugin(Object target) {
        Object origin = target;
        return plugin(interceptorMap.values(), origin, target);
    }

    private Object plugin(Collection<Interceptor> interceptors, Object origin, Object target) {
        if (!ObjectUtil.isNull(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                target = plugin(origin, target, interceptor);
            }
        }
        return target;
    }


    protected abstract Object plugin(Object origin, Object target, Interceptor interceptor);

    @Override
    public <T extends Interceptor> T getInterceptor(Class<T> interceptor) {
        return (T) interceptorMap.get(interceptor);
    }
}
