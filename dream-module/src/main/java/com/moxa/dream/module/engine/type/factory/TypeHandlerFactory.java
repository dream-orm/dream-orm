package com.moxa.dream.module.engine.type.factory;

import com.moxa.dream.module.engine.type.handler.TypeHandler;
import com.moxa.dream.module.engine.type.wrapper.TypeHandlerWrapper;

import java.util.List;


public interface TypeHandlerFactory {
    void wrapper(List<? extends TypeHandlerWrapper> typeHandlerWrapperList);

    TypeHandler getTypeHandler(Class javaType, int jdbcType);

}
