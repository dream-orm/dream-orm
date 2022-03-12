package com.moxa.dream.module.engine.type.wrapper;

import com.moxa.dream.module.engine.type.handler.TypeHandler;

public interface TypeHandlerWrapper {
    TypeHandler getTypeHandler();

    Integer[] typeCode();

}
