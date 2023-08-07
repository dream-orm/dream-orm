package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.TypeHandler;

/**
 * 类型转换器包装类
 */
public interface TypeHandlerWrapper {
    /**
     * 对应的类型转换器
     *
     * @return
     */
    TypeHandler<?> getTypeHandler();

    /**
     * 映射类型转换器执行的编码
     *
     * @return
     */
    Integer[] typeCode();

}
