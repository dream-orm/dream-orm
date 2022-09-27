package com.moxa.dream.mate.logic.interceptor;

import com.moxa.dream.system.plugin.interceptor.Interceptor;
import com.moxa.dream.system.plugin.invocation.Invocation;

import java.lang.reflect.Method;
import java.util.Set;

public class LogicInterceptor implements Interceptor {
    private LogicHandler logicHandler;

    public LogicInterceptor(LogicHandler logicHandler) {
        this.logicHandler = logicHandler;
    }

    @Override
    public Object interceptor(Invocation invocation) throws Throwable {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Method> methods() throws Exception {
        return null;
    }

    public LogicHandler getLogicHandler() {
        return logicHandler;
    }
}
