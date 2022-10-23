package com.moxa.dream.template.annotation;

public enum InjectType {
    NONE(0), INSERT(1), UPDATE(2), INSERT_UPDATE(3);
    private int code;

    InjectType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
