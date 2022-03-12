package com.moxa.dream.module.typehandler.wrapper;

import com.moxa.dream.module.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.typehandler.handler.BlobTypeHandler;
import com.moxa.dream.module.typehandler.util.TypeUtil;

import java.sql.Types;

public class BlobTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new BlobTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.BLOB),
                TypeUtil.hash(byte[].class, Types.BLOB),
                TypeUtil.hash(Byte[].class, Types.BLOB),
                TypeUtil.hash(Object.class, Types.LONGVARBINARY),
                TypeUtil.hash(byte[].class, Types.LONGVARBINARY),
                TypeUtil.hash(Byte[].class, Types.LONGVARBINARY),
                TypeUtil.hash(byte[].class, Types.NULL),
                TypeUtil.hash(Byte[].class, Types.NULL),
        };
    }

}
