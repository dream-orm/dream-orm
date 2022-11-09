package com.moxa.dream.system.core.listener;

import com.moxa.dream.system.config.MappedStatement;

public interface Listener {
    boolean before(MappedStatement mappedStatement);

    void afterReturn(Object result, MappedStatement mappedStatement);

    void exception(Exception e, MappedStatement mappedStatement);
}
