package com.moxa.dream.module.plugin.invocation;

import java.lang.reflect.Method;

public class JavaInvocation implements Invocation {
    private Object target;
    private Method method;
    private Object[] args;

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