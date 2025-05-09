package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.ObjectTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class ObjectTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Object> getTypeHandler() {
        return new ObjectTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.OTHER),
                TypeUtil.hash(Object.class, Types.NULL),
        };
    }

}
