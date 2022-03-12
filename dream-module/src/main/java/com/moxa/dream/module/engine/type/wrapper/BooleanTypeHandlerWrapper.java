package com.moxa.dream.module.engine.type.wrapper;

import com.moxa.dream.module.engine.type.handler.BaseTypeHandler;
import com.moxa.dream.module.engine.type.handler.BooleanTypeHandler;
import com.moxa.dream.module.engine.type.util.TypeUtil;

import java.sql.Types;

public class BooleanTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new BooleanTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.BOOLEAN),
                TypeUtil.hash(boolean.class, Types.BOOLEAN),
                TypeUtil.hash(Boolean.class, Types.BOOLEAN),
                TypeUtil.hash(Object.class, Types.BIT),
                TypeUtil.hash(boolean.class, Types.BIT),
                TypeUtil.hash(Boolean.class, Types.BIT),
                TypeUtil.hash(boolean.class, Types.NULL),
                TypeUtil.hash(Boolean.class, Types.NULL),
        };
    }

}
