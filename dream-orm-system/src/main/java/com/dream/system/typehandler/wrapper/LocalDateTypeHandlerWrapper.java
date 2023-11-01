package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.LocalDateTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;
import java.time.LocalDate;

public class LocalDateTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<LocalDate> getTypeHandler() {
        return new LocalDateTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(LocalDate.class, Types.DATE),
                TypeUtil.hash(LocalDate.class, Types.NULL),
                TypeUtil.hash(LocalDate.class, Types.TIMESTAMP),
        };
    }

}
