package com.moxa.dream.module.engine.typehandler.wrapper;

import com.moxa.dream.module.engine.typehandler.handler.BaseTypeHandler;
import com.moxa.dream.module.engine.typehandler.handler.BlobInputStreamTypeHandler;
import com.moxa.dream.module.engine.typehandler.util.TypeUtil;

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
