package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.system.typehandler.handler.ByteTypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class ByteTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new ByteTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.TINYINT),
                TypeUtil.hash(byte.class, Types.TINYINT),
                TypeUtil.hash(Byte.class, Types.TINYINT),
                TypeUtil.hash(byte.class, Types.NULL),
                TypeUtil.hash(Byte.class, Types.NULL),
        };
    }

}
