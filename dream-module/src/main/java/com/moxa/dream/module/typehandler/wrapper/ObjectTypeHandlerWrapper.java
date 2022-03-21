package com.moxa.dream.module.typehandler.wrapper;

import com.moxa.dream.module.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.typehandler.handler.ObjectTypeHandler;
import com.moxa.dream.module.typehandler.util.TypeUtil;

import java.sql.Types;

public class ObjectTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new ObjectTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.NULL),
        };
    }

}
