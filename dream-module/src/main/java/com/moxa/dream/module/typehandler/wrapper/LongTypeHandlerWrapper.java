package com.moxa.dream.module.typehandler.wrapper;

import com.moxa.dream.module.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.typehandler.handler.LongTypeHandler;
import com.moxa.dream.module.typehandler.util.TypeUtil;

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
