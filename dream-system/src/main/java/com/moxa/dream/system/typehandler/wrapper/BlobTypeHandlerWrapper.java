package com.moxa.dream.system.typehandler.wrapper;

import com.moxa.dream.system.typehandler.handler.BlobTypeHandler;
import com.moxa.dream.system.typehandler.handler.TypeHandler;
import com.moxa.dream.system.typehandler.util.TypeUtil;

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
                TypeUtil.hash(Object.class, Types.BINARY),
                TypeUtil.hash(byte[].class, Types.BINARY),
                TypeUtil.hash(Byte[].class, Types.BINARY),
                TypeUtil.hash(Object.class, Types.VARBINARY),
                TypeUtil.hash(byte[].class, Types.VARBINARY),
                TypeUtil.hash(Byte[].class, Types.VARBINARY),
                TypeUtil.hash(Object.class, Types.LONGVARBINARY),
                TypeUtil.hash(byte[].class, Types.LONGVARBINARY),
                TypeUtil.hash(Byte[].class, Types.LONGVARBINARY),
                TypeUtil.hash(byte[].class, Types.NULL),
                TypeUtil.hash(Byte[].class, Types.NULL),
        };
    }

}
