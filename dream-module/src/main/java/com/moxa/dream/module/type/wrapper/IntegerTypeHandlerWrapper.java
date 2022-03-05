package com.moxa.dream.module.type.wrapper;

import com.moxa.dream.module.type.handler.BaseTypeHandler;
import com.moxa.dream.module.type.handler.IntegerTypeHandler;
import com.moxa.dream.module.type.util.TypeUtil;

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
