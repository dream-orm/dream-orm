package com.moxa.dream.antlr.callback;

public class CallbackException extends RuntimeException {
    public CallbackException(String msg, Exception e) {
        super(msg, e);
    }

    public CallbackException(String msg) {
        super(msg);
    }
}
