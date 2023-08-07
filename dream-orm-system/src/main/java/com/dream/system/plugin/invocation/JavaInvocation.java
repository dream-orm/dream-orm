package com.dream.system.plugin.invocation;

import java.lang.reflect.Method;

public class JavaInvocation implements Invocation {
    private final Object target;
    private final Method method;
    private final Object[] args;

    public JavaInvocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target, args);
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Object[] getArgs() {
        return args;
    }
}