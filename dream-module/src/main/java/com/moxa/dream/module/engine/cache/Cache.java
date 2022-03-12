package com.moxa.dream.module.engine.cache;

import com.moxa.dream.module.hold.mapped.MappedStatement;

public interface Cache {
    void put(MappedStatement mappedStatement, Object value);

    Object get(MappedStatement mappedStatement);

    void remove(MappedStatement mappedStatement);

    void clear();
}
