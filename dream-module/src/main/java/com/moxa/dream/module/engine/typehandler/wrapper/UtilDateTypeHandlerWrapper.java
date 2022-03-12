package com.moxa.dream.module.engine.typehandler.wrapper;

import com.moxa.dream.module.engine.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.engine.typehandler.handler.UtilDateTypeHandler;
import com.moxa.dream.module.engine.typehandler.util.TypeUtil;

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
