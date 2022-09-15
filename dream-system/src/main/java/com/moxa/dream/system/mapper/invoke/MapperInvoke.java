package com.moxa.dream.system.mapper.invoke;

import com.moxa.dream.system.mapper.MethodInfo;

public interface MapperInvoke {
    Object invoke(MethodInfo methodInfo, Object arg) throws Exception;
}
