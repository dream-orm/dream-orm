package com.moxa.dream.system.mapper.handler;

import com.moxa.dream.system.mapper.MethodInfo;

public interface MapperHandler {
    Object handler(MethodInfo methodInfo, Object arg) throws Exception;
}
