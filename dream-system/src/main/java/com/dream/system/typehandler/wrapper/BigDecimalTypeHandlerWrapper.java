package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.BigDecimalTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.math.BigDecimal;
import java.sql.Types;

public class BigDecimalTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<BigDecimal> getTypeHandler() {
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
