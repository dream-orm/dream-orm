package com.moxa.dream.system.config;

public class MethodParam {
    private String paramName;
    private Class<?> paramType;

    public MethodParam(String paramName, Class<?> paramType) {
        this.paramName = paramName;
        this.paramType = paramType;
    }

    public String getParamName() {
        return paramName;
    }

    public Class<?> getParamType() {
        return paramType;
    }
}
