package com.moxa.dream.system.mapper;

import com.moxa.dream.system.config.MethodInfo;

import java.util.Map;

public interface MapperInvoke {
    Object invoke(MethodInfo methodInfo, Map<String, Object> argMap, Class<?> type);
}
