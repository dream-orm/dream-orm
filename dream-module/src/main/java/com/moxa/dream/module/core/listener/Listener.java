package com.moxa.dream.module.core.listener;

import com.moxa.dream.module.mapped.MappedStatement;

public interface Listener {
    void before(MappedStatement mappedStatement);

    void afterReturn(Object result, MappedStatement mappedStatement);

    void exception(Exception e, MappedStatement mappedStatement);

    void after(MappedStatement mappedStatement);
}
