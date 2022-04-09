package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.system.typehandler.handler.SqlDateTypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

import java.sql.Date;
import java.sql.Types;

public class SqlDateTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
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
