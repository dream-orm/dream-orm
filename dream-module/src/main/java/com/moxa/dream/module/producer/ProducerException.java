package com.moxa.dream.module.producer;

public class ProducerException extends RuntimeException {
    public ProducerException(String msg) {
        super(msg);
    }

    public ProducerException(Exception e) {
        super(e);
    }
}
