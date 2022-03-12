package com.moxa.dream.test.core;

import com.moxa.dream.module.plugin.interceptor.Interceptor;
import com.moxa.dream.module.plugin.invocation.Invocation;

import java.lang.reflect.Method;
import java.util.Set;

public class MyInterceptor implements Interceptor {
    @Override
    public Object interceptor(Invocation invocation) {
        return null;
    }

    @Override
    public Set<Method> methods() {
        return null;
    }
}
