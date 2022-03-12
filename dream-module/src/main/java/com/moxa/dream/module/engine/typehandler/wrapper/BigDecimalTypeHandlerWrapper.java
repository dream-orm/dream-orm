package com.moxa.dream.module.engine.typehandler.wrapper;

import com.moxa.dream.module.engine.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.engine.typehandler.handler.BigDecimalTypeHandler;
import com.moxa.dream.module.engine.typehandler.util.TypeUtil;

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
