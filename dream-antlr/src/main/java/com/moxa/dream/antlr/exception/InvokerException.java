package com.moxa.dream.antlr.exception;

public class InvokerException extends Exception {
    public InvokerException(String message) {
        super(message);
    }

    public InvokerException(String message, Exception e) {
        super(message, e);
    }
}
