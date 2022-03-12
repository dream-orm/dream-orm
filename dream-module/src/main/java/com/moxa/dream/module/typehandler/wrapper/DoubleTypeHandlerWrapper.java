package com.moxa.dream.module.typehandler.wrapper;

import com.moxa.dream.module.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.typehandler.handler.DoubleTypeHandler;
import com.moxa.dream.module.typehandler.util.TypeUtil;

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
