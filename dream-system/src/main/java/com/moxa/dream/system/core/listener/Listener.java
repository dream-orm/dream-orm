package com.moxa.dream.system.core.listener;

import com.moxa.dream.system.mapped.MappedStatement;

public interface Listener {
    void before(MappedStatement mappedStatement);

    void afterReturn(Object result, MappedStatement mappedStatement);

    void exception(Exception e, MappedStatement mappedStatement);
}
