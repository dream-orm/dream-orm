package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.StringArrayTypeHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class StringArrayTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<String[]> getTypeHandler() {
        return new StringArrayTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(String[].class, Types.CHAR),
                TypeUtil.hash(String[].class, Types.CLOB),
                TypeUtil.hash(String[].class, Types.VARCHAR),
                TypeUtil.hash(String[].class, Types.LONGVARCHAR),
                TypeUtil.hash(String[].class, Types.LONGNVARCHAR),
                TypeUtil.hash(String[].class, Types.NVARCHAR),
                TypeUtil.hash(String[].class, Types.NCHAR),
                TypeUtil.hash(String[].class, Types.NCLOB),
                TypeUtil.hash(String[].class, Types.NULL),
        };
    }

}
