package com.moxa.dream.module.type.wrapper;

import com.moxa.dream.module.type.handler.TypeHandler;

public interface TypeHandlerWrapper {
    TypeHandler getTypeHandler();

    Integer[] typeCode();

}
