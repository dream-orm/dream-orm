package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.SqlDateTypeHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

import java.sql.Date;
import java.sql.Types;

public class SqlDateTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Date> getTypeHandler() {
        return new SqlDateTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.DATE),
                TypeUtil.hash(Date.class, Types.DATE),
                TypeUtil.hash(Date.class, Types.NULL),
        };
    }

}
