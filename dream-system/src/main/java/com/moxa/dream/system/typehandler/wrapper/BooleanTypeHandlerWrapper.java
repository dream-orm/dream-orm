package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.BooleanTypeHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class BooleanTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Boolean> getTypeHandler() {
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
                TypeUtil.hash(boolean.class, Types.TINYINT),
                TypeUtil.hash(Boolean.class, Types.TINYINT),
                TypeUtil.hash(boolean.class, Types.SMALLINT),
                TypeUtil.hash(Boolean.class, Types.SMALLINT),
                TypeUtil.hash(boolean.class, Types.NULL),
                TypeUtil.hash(Boolean.class, Types.NULL),
        };
    }

}
