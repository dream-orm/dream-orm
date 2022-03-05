package com.moxa.dream.module.type.wrapper;

import com.moxa.dream.module.type.handler.BaseTypeHandler;
import com.moxa.dream.module.type.handler.TimeStampTypeHandler;
import com.moxa.dream.module.type.util.TypeUtil;

import java.sql.Timestamp;
import java.sql.Types;

public class TimeStampTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new TimeStampTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.TIMESTAMP),
                TypeUtil.hash(Timestamp.class, Types.TIMESTAMP),
                TypeUtil.hash(Timestamp.class, Types.NULL),
        };
    }

}
