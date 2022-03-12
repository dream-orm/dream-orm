package com.moxa.dream.module.engine.typehandler.wrapper;

import com.moxa.dream.module.engine.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.engine.typehandler.handler.ShortTypeHandler;
import com.moxa.dream.module.engine.typehandler.util.TypeUtil;

import java.sql.Types;

public class ShortTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new ShortTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.SMALLINT),
                TypeUtil.hash(short.class, Types.SMALLINT),
                TypeUtil.hash(Short.class, Types.SMALLINT),
                TypeUtil.hash(short.class, Types.NULL),
                TypeUtil.hash(Short.class, Types.NULL),
        };
    }

}
