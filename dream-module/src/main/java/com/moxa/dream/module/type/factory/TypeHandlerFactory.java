package com.moxa.dream.module.type.factory;

import com.moxa.dream.module.type.handler.TypeHandler;
import com.moxa.dream.module.type.wrapper.TypeHandlerWrapper;

import java.util.List;


public interface TypeHandlerFactory {
    void wrapper(List<? extends TypeHandlerWrapper> typeHandlerWrapperList);

    TypeHandler getTypeHandler(Class javaType, int jdbcType);

}
