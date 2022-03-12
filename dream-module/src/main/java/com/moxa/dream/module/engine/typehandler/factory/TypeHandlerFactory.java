package com.moxa.dream.module.engine.typehandler.factory;

import com.moxa.dream.module.engine.typehandler.handler.TypeHandler;
import com.moxa.dream.module.engine.typehandler.wrapper.TypeHandlerWrapper;

import java.util.List;


public interface TypeHandlerFactory {
    void wrapper(List<? extends TypeHandlerWrapper> typeHandlerWrapperList);

    TypeHandler getTypeHandler(Class javaType, int jdbcType);

}
