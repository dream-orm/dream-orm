package com.moxa.dream.system.mapper.handler;

import com.moxa.dream.system.mapper.MethodInfo;

public interface MapperHandler {
    Object invoke(MethodInfo methodInfo, Object arg, Object... args) throws Exception;
}
