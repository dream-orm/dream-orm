package com.dream.system.typehandler.factory;

import com.dream.system.typehandler.TypeHandlerNotFoundException;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.wrapper.TypeHandlerWrapper;


/**
 * 类型转换器包装创建类
 */
public interface TypeHandlerFactory {
    /**
     * 新增类型转换器包装
     *
     * @param typeHandlerWrappers
     */
    void wrappers(TypeHandlerWrapper... typeHandlerWrappers);

    /**
     * 根据java类型和数据库类型获取对应类型转换器
     *
     * @param javaType
     * @param jdbcType
     * @return
     * @throws TypeHandlerNotFoundException
     */
    TypeHandler getTypeHandler(Class javaType, int jdbcType) throws TypeHandlerNotFoundException;

}
