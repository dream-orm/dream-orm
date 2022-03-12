package com.moxa.dream.test.core;

import com.moxa.dream.module.typehandler.handler.TypeHandler;
import com.moxa.dream.module.typehandler.wrapper.TypeHandlerWrapper;

public class MyTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler getTypeHandler() {
        return null;
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[0];
    }
}
