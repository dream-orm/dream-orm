package com.moxa.dream.system.typehandler.factory;

import com.moxa.dream.system.typehandler.TypeHandlerNotFoundException;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.wrapper.TypeHandlerWrapper;


public interface TypeHandlerFactory {
    void wrappers(TypeHandlerWrapper... typeHandlerWrappers);

    TypeHandler getTypeHandler(Class javaType, int jdbcType) throws TypeHandlerNotFoundException;

}
