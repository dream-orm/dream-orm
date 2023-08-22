package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.StringTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

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
                TypeUtil.hash(Object.class, Types.LONGNVARCHAR),
                TypeUtil.hash(Object.class, Types.NVARCHAR),
                TypeUtil.hash(Object.class, Types.NCHAR),
                TypeUtil.hash(Object.class, Types.NCLOB),
                TypeUtil.hash(String.class, Types.CHAR),
                TypeUtil.hash(String.class, Types.CLOB),
                TypeUtil.hash(String.class, Types.VARCHAR),
                TypeUtil.hash(String.class, Types.LONGVARCHAR),
                TypeUtil.hash(String.class, Types.LONGNVARCHAR),
                TypeUtil.hash(String.class, Types.NVARCHAR),
                TypeUtil.hash(String.class, Types.NCHAR),
                TypeUtil.hash(String.class, Types.NCLOB),
                TypeUtil.hash(String.class, Types.BINARY),
                TypeUtil.hash(Object.class, Types.BINARY),
                TypeUtil.hash(String.class, Types.VARBINARY),
                TypeUtil.hash(Object.class, Types.VARBINARY),
                TypeUtil.hash(String.class, Types.LONGVARBINARY),
                TypeUtil.hash(Object.class, Types.LONGVARBINARY),
                TypeUtil.hash(String.class, Types.NULL),
        };
    }

}
