package com.moxa.dream.module.type.wrapper;

import com.moxa.dream.module.type.handler.BaseTypeHandler;
import com.moxa.dream.module.type.handler.BlobInputStreamTypeHandler;
import com.moxa.dream.module.type.util.TypeUtil;

import java.io.InputStream;
import java.sql.Types;

public class BlobInputStreamTypeHandlerWrapper implements TypeHandlerWrapper {
    @Override
    public BaseTypeHandler getTypeHandler() {
        return new BlobInputStreamTypeHandler();
    }

    @Override
    public Integer[] typeCode() {
        return new Integer[]{
                TypeUtil.hash(InputStream.class, Types.BLOB),
                TypeUtil.hash(InputStream.class, Types.NULL),
        };
    }

}
