package com.moxa.dream.module.plugin.interceptor;

import java.lang.reflect.Method;
import java.util.Set;

public abstract class AbstractInterceptor implements Interceptor {
    private Set<Method> methods;

    public Set<Method> methods() {
        if (methods == null) {
            methods = methodSet();
        }
        return methods;
    }

    protected abstract Set<Method> methodSet();
}
