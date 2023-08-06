package com.dream.util.exception;

public class DreamException extends Exception {
    public DreamException(String msg) {
        super(msg);
    }

    public DreamException(String msg, Exception e) {
        super(msg, e);
    }
}
