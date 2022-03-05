package com.moxa.dream.util.reflect;

public class ReflectException extends RuntimeException {
    public ReflectException(String msg) {
        super(msg);
    }

    public ReflectException(String msg, Exception e) {
        super(msg, e);
    }

    public ReflectException(Exception e) {
        super(e);
    }
}
