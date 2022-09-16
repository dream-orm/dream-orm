package com.moxa.dream.system.mapper;

import com.moxa.dream.system.mapped.MethodInfo;

public interface MapperInvoke {
    Object invoke(MethodInfo methodInfo, Object arg);
}
