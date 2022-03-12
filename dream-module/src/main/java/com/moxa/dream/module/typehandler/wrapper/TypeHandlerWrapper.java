package com.moxa.dream.module.typehandler.wrapper;

import com.moxa.dream.module.typehandler.handler.TypeHandler;

public interface TypeHandlerWrapper {
    TypeHandler getTypeHandler();

    Integer[] typeCode();

}
