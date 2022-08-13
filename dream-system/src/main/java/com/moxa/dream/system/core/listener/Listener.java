package com.moxa.dream.system.core.listener;

import com.moxa.dream.system.mapped.MappedStatement;

public interface Listener {
    boolean before(MappedStatement mappedStatement);

    Object afterReturn(Object result, MappedStatement mappedStatement);

    void exception(Exception e, MappedStatement mappedStatement);
}
