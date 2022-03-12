package com.moxa.dream.module.engine.typehandler.wrapper;

import com.moxa.dream.module.engine.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.engine.typehandler.handler.FloatTypeHandler;
import com.moxa.dream.module.engine.typehandler.util.TypeUtil;

import java.sql.Types;

public class FloatTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new FloatTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.FLOAT),
                TypeUtil.hash(float.class, Types.FLOAT),
                TypeUtil.hash(Float.class, Types.FLOAT),
                TypeUtil.hash(Object.class, Types.REAL),
                TypeUtil.hash(float.class, Types.REAL),
                TypeUtil.hash(Float.class, Types.REAL),
                TypeUtil.hash(float.class, Types.NULL),
                TypeUtil.hash(Float.class, Types.NULL),
        };
    }

}
