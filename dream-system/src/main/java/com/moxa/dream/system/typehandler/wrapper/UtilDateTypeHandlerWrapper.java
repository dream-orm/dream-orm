package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.system.typehandler.handler.UtilDateTypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;
import java.util.Date;

public class UtilDateTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new UtilDateTypeHandler();
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
