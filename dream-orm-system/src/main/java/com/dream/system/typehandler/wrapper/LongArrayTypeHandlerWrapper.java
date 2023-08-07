package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.LongArrayTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class LongArrayTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Long[]> getTypeHandler() {
        return new LongArrayTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Long[].class, Types.CHAR),
                TypeUtil.hash(Long[].class, Types.CLOB),
                TypeUtil.hash(Long[].class, Types.VARCHAR),
                TypeUtil.hash(Long[].class, Types.LONGVARCHAR),
                TypeUtil.hash(Long[].class, Types.LONGNVARCHAR),
                TypeUtil.hash(Long[].class, Types.NVARCHAR),
                TypeUtil.hash(Long[].class, Types.NCHAR),
                TypeUtil.hash(Long[].class, Types.NCLOB),
                TypeUtil.hash(Long[].class, Types.NULL),
        };
    }

}
