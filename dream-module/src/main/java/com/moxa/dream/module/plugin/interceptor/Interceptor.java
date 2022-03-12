package com.moxa.dream.module.plugin.interceptor;

import com.moxa.dream.module.plugin.invocation.Invocation;

import java.lang.reflect.Method;
import java.util.Set;

public interface Interceptor {
    Object interceptor(Invocation invocation) throws Throwable;

    Set<Method> methods();
}
