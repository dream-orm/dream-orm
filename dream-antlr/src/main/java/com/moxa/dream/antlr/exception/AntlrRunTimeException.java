package com.moxa.dream.antlr.exception;

public class AntlrRunTimeException extends RuntimeException {
    public AntlrRunTimeException(String message) {
        super(message);
    }

    public AntlrRunTimeException(String message, Exception e) {
        super(message, e);
    }
}
