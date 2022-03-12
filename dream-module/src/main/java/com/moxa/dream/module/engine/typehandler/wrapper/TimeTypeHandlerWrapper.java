package com.moxa.dream.module.engine.typehandler.wrapper;

import com.moxa.dream.module.engine.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.engine.typehandler.util.TypeUtil;

import java.sql.Time;
import java.sql.Types;

public class TimeTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return null;
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
