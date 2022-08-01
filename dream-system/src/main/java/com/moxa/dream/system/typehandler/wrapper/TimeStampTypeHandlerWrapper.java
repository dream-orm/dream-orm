package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.TimeStampTypeHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

import java.sql.Timestamp;
import java.sql.Types;

public class TimeStampTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Timestamp> getTypeHandler() {
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
