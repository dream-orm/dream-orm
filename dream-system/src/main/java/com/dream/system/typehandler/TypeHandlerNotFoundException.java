package com.dream.system.typehandler;

import com.dream.util.exception.DreamException;

public class TypeHandlerNotFoundException extends DreamException {
    public TypeHandlerNotFoundException(String msg) {
        super(msg);
    }

    public TypeHandlerNotFoundException(String msg, Exception e) {
        super(msg, e);
    }
}
