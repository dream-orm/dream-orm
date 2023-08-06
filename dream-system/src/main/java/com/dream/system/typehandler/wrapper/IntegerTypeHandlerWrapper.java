package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.IntegerTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class IntegerTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<Integer> getTypeHandler() {
        return new IntegerTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.INTEGER),
                TypeUtil.hash(Object.class, Types.SMALLINT),
                TypeUtil.hash(int.class, Types.SMALLINT),
                TypeUtil.hash(Integer.class, Types.SMALLINT),
                TypeUtil.hash(int.class, Types.INTEGER),
                TypeUtil.hash(Integer.class, Types.INTEGER),
                TypeUtil.hash(int.class, Types.BIGINT),
                TypeUtil.hash(Integer.class, Types.BIGINT),
                TypeUtil.hash(int.class, Types.NUMERIC),
                TypeUtil.hash(Integer.class, Types.NUMERIC),
                TypeUtil.hash(int.class, Types.BIT),
                TypeUtil.hash(Integer.class, Types.BIT),
                TypeUtil.hash(int.class, Types.TINYINT),
                TypeUtil.hash(Integer.class, Types.TINYINT),
                TypeUtil.hash(int.class, Types.NULL),
                TypeUtil.hash(Integer.class, Types.NULL),
        };
    }

}
