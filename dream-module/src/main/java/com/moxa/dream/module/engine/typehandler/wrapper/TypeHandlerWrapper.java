package com.moxa.dream.module.engine.typehandler.wrapper;

import com.moxa.dream.module.engine.typehandler.handler.TypeHandler;

public interface TypeHandlerWrapper {
    TypeHandler getTypeHandler();

    Integer[] typeCode();

}
