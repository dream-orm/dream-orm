package com.moxa.dream.util.resource;

public class ResourceException extends RuntimeException {
    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Exception e) {
        super(message, e);
    }

    public ResourceException(Exception e) {
        super(e);
    }
}
