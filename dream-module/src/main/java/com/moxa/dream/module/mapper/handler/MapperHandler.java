package com.moxa.dream.module.mapper.handler;

import com.moxa.dream.module.mapper.MethodInfo;

public interface MapperHandler {
    Object invoke(MethodInfo methodInfo, Object arg, Object... args) throws Exception;
}
