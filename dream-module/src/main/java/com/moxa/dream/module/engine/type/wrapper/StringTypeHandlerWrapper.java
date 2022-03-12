package com.moxa.dream.module.engine.type.wrapper;

import com.moxa.dream.module.engine.type.handler.BaseTypeHandler;
import com.moxa.dream.module.engine.type.handler.StringTypeHandler;
import com.moxa.dream.module.engine.type.util.TypeUtil;

import java.sql.Types;

public class StringTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new StringTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.CHAR),
                TypeUtil.hash(Object.class, Types.CLOB),
                TypeUtil.hash(Object.class, Types.VARCHAR),
                TypeUtil.hash(Object.class, Types.LONGVARCHAR),
                TypeUtil.hash(Object.class, Types.NVARCHAR),
                TypeUtil.hash(Object.class, Types.NCHAR),
                TypeUtil.hash(Object.class, Types.NCLOB),
                TypeUtil.hash(String.class, Types.CHAR),
                TypeUtil.hash(String.class, Types.CLOB),
                TypeUtil.hash(String.class, Types.VARCHAR),
                TypeUtil.hash(String.class, Types.LONGVARCHAR),
                TypeUtil.hash(String.class, Types.NVARCHAR),
                TypeUtil.hash(String.class, Types.NCHAR),
                TypeUtil.hash(String.class, Types.NCLOB),
                TypeUtil.hash(String.class, Types.NULL),
        };
    }

}
