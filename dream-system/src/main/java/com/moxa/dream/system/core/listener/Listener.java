package com.moxa.dream.system.core.listener;

import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapped.MappedStatement;

public interface Listener {
    boolean before(MappedStatement mappedStatement, Executor executor);

    Object afterReturn(Object result, MappedStatement mappedStatement,Executor executor);

    void exception(Exception e, MappedStatement mappedStatement,Executor executor);
}
