package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.LocalDateTimeTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;
import java.time.LocalDateTime;

public class LocalDateTimeTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<LocalDateTime> getTypeHandler() {
        return new LocalDateTimeTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(LocalDateTime.class, Types.DATE),
                TypeUtil.hash(LocalDateTime.class, Types.NULL),
                TypeUtil.hash(LocalDateTime.class, Types.TIMESTAMP),
        };
    }

}
