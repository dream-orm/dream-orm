package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.handler.UtilDateTypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Date;

public class LocalDateTimeTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Date> getTypeHandler() {
        return new UtilDateTypeHandler();
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
