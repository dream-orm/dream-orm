package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.TimeTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.sql.Time;
import java.sql.Types;

public class TimeTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Time> getTypeHandler() {
        return new TimeTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.TIME),
                TypeUtil.hash(Time.class, Types.TIME),
                TypeUtil.hash(Time.class, Types.NULL),
        };
    }

}
