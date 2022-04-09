package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.TypeHandler;

public interface TypeHandlerWrapper {
    TypeHandler getTypeHandler();

    Integer[] typeCode();

}
