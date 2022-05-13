package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.StringTypeHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class StringTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<String> getTypeHandler() {
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
                TypeUtil.hash(String.class, Types.INTEGER),
                TypeUtil.hash(String.class, Types.NUMERIC),
                TypeUtil.hash(String.class, Types.DOUBLE),
                TypeUtil.hash(String.class, Types.FLOAT),
                TypeUtil.hash(String.class, Types.BIGINT),
                TypeUtil.hash(String.class, Types.BOOLEAN),
                TypeUtil.hash(String.class, Types.TIME),
                TypeUtil.hash(String.class, Types.SMALLINT),
                TypeUtil.hash(String.class, Types.TINYINT),
                TypeUtil.hash(String.class, Types.BIT),
                TypeUtil.hash(String.class, Types.DECIMAL),
                TypeUtil.hash(String.class, Types.REAL),
                TypeUtil.hash(String.class, Types.DATE),
                TypeUtil.hash(String.class, Types.TIMESTAMP),
                TypeUtil.hash(String.class, Types.NULL),
        };
    }

}
