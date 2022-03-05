package com.moxa.dream.test.core;

import com.moxa.dream.module.type.handler.TypeHandler;
import com.moxa.dream.module.type.wrapper.TypeHandlerWrapper;

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
