package com.dream.template.annotation;

public enum WrapType {
    NONE(0), INSERT(1), UPDATE(2), INSERT_UPDATE(3);
    private int code;

    WrapType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
