package com.moxa.dream.module.plugin;

import java.lang.reflect.Method;
import java.util.Set;

public interface Interceptor {
    Object interceptor(Invocation invocation) throws Throwable;

    Set<Method> methods() throws Exception;
}
