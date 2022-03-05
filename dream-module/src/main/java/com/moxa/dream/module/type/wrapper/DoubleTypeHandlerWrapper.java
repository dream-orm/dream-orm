package com.moxa.dream.module.type.wrapper;

import com.moxa.dream.module.type.handler.BaseTypeHandler;
import com.moxa.dream.module.type.handler.DoubleTypeHandler;
import com.moxa.dream.module.type.util.TypeUtil;

import java.sql.Types;

public class DoubleTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new DoubleTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.DOUBLE),
                TypeUtil.hash(double.class, Types.DOUBLE),
                TypeUtil.hash(Double.class, Types.DOUBLE),
                TypeUtil.hash(double.class, Types.NULL),
                TypeUtil.hash(Double.class, Types.NULL),
        };
    }

}
