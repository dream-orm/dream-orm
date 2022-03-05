package com.moxa.dream.module.cache;

import com.moxa.dream.module.mapped.MappedStatement;

public interface Cache {
    void put(MappedStatement mappedStatement, Object value);

    Object get(MappedStatement mappedStatement);

    void remove(MappedStatement mappedStatement);

    void clear();
}
