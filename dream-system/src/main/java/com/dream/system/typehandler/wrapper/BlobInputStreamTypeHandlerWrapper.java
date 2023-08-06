package com.dream.system.typehandler.wrapper;

import com.dream.system.typehandler.handler.BlobInputStreamTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.typehandler.util.TypeUtil;

import java.io.InputStream;
import java.sql.Types;

public class BlobInputStreamTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public TypeHandler<InputStream> getTypeHandler() {
        return new BlobInputStreamTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(Object.class, Types.BLOB),
                TypeUtil.hash(InputStream.class, Types.BLOB),
                TypeUtil.hash(InputStream.class, Types.NULL),
        };
    }

}
