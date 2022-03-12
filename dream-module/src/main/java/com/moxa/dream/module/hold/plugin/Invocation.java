package com.moxa.dream.module.hold.plugin;

import java.lang.reflect.Method;

public interface Invocation {
    Object proceed() throws Throwable;

    Object getTarget();

    Method getMethod();

    Object[] getArgs();
}
