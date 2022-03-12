package com.moxa.dream.module.engine.type.wrapper;

import com.moxa.dream.module.engine.type.handler.BaseTypeHandler;
import com.moxa.dream.module.engine.type.handler.LongTypeHandler;
import com.moxa.dream.module.engine.type.util.TypeUtil;

import java.sql.Types;

public class LongTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new LongTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.BIGINT),
                TypeUtil.hash(long.class, Types.BIGINT),
                TypeUtil.hash(Long.class, Types.BIGINT),
                TypeUtil.hash(long.class, Types.NULL),
                TypeUtil.hash(Long.class, Types.NULL),
        };
    }

}
