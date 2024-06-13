package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.FloatTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class FloatTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Float> getTypeHandler() {
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
                TypeUtil.hash(float.class, Types.NUMERIC),
                TypeUtil.hash(Float.class, Types.NUMERIC),
                TypeUtil.hash(float.class, Types.DOUBLE),
                TypeUtil.hash(Float.class, Types.DOUBLE),
                TypeUtil.hash(float.class, Types.DECIMAL),
                TypeUtil.hash(Float.class, Types.DECIMAL),
                TypeUtil.hash(float.class, Types.NULL),
                TypeUtil.hash(Float.class, Types.NULL),
        };
    }

}
