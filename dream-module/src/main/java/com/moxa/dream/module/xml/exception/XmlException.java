package com.moxa.dream.module.xml.exception;

public class XmlException extends RuntimeException {

    public XmlException(String msg) {
        super(msg);
    }

    public XmlException(Exception e) {
        super(e);
    }
}
