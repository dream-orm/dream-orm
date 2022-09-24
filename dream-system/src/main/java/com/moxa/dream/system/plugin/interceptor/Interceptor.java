package com.moxa.dream.system.plugin.interceptor;

import com.moxa.dream.system.plugin.invocation.Invocation;

import java.lang.reflect.Method;
import java.util.Set;

public interface Interceptor {
    Object interceptor(Invocation invocation) throws Throwable;

    Set<Method> methods() throws Exception;
}
