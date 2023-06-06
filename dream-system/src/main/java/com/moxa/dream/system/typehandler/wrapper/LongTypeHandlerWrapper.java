package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.LongTypeHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class LongTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Long> getTypeHandler() {
        return new LongTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.BIGINT),
                TypeUtil.hash(long.class, Types.BIGINT),
                TypeUtil.hash(Long.class, Types.BIGINT),
                TypeUtil.hash(long.class, Types.INTEGER),
                TypeUtil.hash(Long.class, Types.INTEGER),
                TypeUtil.hash(long.class, Types.NUMERIC),
                TypeUtil.hash(Long.class, Types.NUMERIC),
                TypeUtil.hash(long.class, Types.NULL),
                TypeUtil.hash(Long.class, Types.NULL),
        };
    }

}
