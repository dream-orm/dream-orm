package com.dream.system.plugin.factory;

import com.dream.system.plugin.interceptor.Interceptor;
import com.dream.util.common.ObjectUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPluginFactory implements PluginFactory {
    private Map<Class, Interceptor> interceptorMap = new HashMap<>(4);

    @Override
    public void interceptors(Interceptor... interceptors) {
        for (Interceptor interceptor : interceptors) {
            interceptorMap.put(interceptor.getClass(), interceptor);
        }
    }

    @Override
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
}
