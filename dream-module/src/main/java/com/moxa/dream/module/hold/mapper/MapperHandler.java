package com.moxa.dream.module.hold.mapper;

public interface MapperHandler {
    Object invoke(MethodInfo methodInfo, Object arg, Object... args) throws Exception;
}
