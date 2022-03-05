package com.moxa.dream.module.type.wrapper;

import com.moxa.dream.module.type.handler.BaseTypeHandler;
import com.moxa.dream.module.type.handler.BigDecimalTypeHandler;
import com.moxa.dream.module.type.util.TypeUtil;

import java.math.BigDecimal;
import java.sql.Types;

public class BigDecimalTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new BigDecimalTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.DECIMAL),
                TypeUtil.hash(BigDecimal.class, Types.DECIMAL),
                TypeUtil.hash(Object.class, Types.NUMERIC),
                TypeUtil.hash(BigDecimal.class, Types.NUMERIC),
                TypeUtil.hash(BigDecimal.class, Types.NULL),
        };
    }

}
