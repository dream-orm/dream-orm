package com.dream.util.exception;

public class DreamException extends Exception {
    public DreamException(String msg) {
        super(msg);
    }

    public DreamException(String msg, Throwable e) {
        super(msg, e);
    }
}
