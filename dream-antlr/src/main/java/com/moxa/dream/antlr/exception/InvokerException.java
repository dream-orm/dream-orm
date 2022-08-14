package com.moxa.dream.antlr.exception;

import com.moxa.dream.util.exception.DreamException;

public class InvokerException extends DreamException {
    public InvokerException(String message) {
        super(message);
    }

    public InvokerException(String message, Exception e) {
        super(message, e);
    }
}
