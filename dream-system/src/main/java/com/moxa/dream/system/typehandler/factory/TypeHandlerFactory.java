package com.moxa.dream.system.typehandler.factory;

import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.wrapper.TypeHandlerWrapper;

import java.util.List;


public interface TypeHandlerFactory {
    void wrapper(List<? extends TypeHandlerWrapper> typeHandlerWrapperList);

    TypeHandler getTypeHandler(Class javaType, int jdbcType);

}
