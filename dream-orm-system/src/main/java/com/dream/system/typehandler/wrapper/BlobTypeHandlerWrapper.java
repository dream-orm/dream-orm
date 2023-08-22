package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.BlobTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.sql.Types;

public class BlobTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<byte[]> getTypeHandler() {
        return new BlobTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.BLOB),
                TypeUtil.hash(byte[].class, Types.BLOB),
                TypeUtil.hash(Byte[].class, Types.BLOB),
                TypeUtil.hash(byte[].class, Types.NULL),
                TypeUtil.hash(Byte[].class, Types.NULL),
        };
    }

}
