package com.moxa.dream.module.engine.listener;

import com.moxa.dream.module.engine.executor.Executor;
import com.moxa.dream.module.mapped.MappedStatement;

public interface ExecutorListener {
    void before(Executor executor, MappedStatement mappedStatement);

    void afterReturn(Object result, Executor executor, MappedStatement mappedStatement);

    void exception(Exception e, Executor executor, MappedStatement mappedStatement);

    void after(Executor executor, MappedStatement mappedStatement);
}
