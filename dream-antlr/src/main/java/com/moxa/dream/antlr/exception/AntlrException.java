package com.moxa.dream.antlr.exception;

public class AntlrException extends Exception {
    public AntlrException(String message) {
        super(message);
    }

    public AntlrException(String message, Exception e) {
        super(message, e);
    }
}
