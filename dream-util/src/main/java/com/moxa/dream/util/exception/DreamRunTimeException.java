package com.moxa.dream.util.exception;

public class DreamRunTimeException extends RuntimeException {
    public DreamRunTimeException(String msg) {
        super(msg);
    }

    public DreamRunTimeException(Exception e) {
        super(e);
    }

    public DreamRunTimeException(String msg, Exception e) {
        super(msg,e);
    }
}
