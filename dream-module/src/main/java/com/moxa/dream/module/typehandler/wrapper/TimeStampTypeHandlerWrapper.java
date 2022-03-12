package com.moxa.dream.module.typehandler.wrapper;

import com.moxa.dream.module.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.typehandler.handler.TimeStampTypeHandler;
import com.moxa.dream.module.typehandler.util.TypeUtil;

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
