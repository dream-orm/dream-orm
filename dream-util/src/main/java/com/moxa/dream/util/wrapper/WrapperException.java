package com.moxa.dream.util.wrapper;

public class WrapperException extends RuntimeException {
    public WrapperException(String msg) {
        super(msg);
    }

    public WrapperException(String msg, Exception e) {
        super(msg, e);
    }
}
