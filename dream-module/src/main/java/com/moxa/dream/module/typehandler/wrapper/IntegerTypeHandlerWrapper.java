package com.moxa.dream.module.typehandler.wrapper;

import com.moxa.dream.module.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.typehandler.handler.IntegerTypeHandler;
import com.moxa.dream.module.typehandler.util.TypeUtil;

import java.sql.Types;

public class IntegerTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new IntegerTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.INTEGER),
                TypeUtil.hash(int.class, Types.INTEGER),
                TypeUtil.hash(Integer.class, Types.INTEGER),
                TypeUtil.hash(int.class, Types.NULL),
                TypeUtil.hash(Integer.class, Types.NULL),
        };
    }

}
