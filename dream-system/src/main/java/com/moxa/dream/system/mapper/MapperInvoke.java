package com.moxa.dream.system.mapper;

import com.moxa.dream.system.config.MethodInfo;

import java.util.Map;

public interface MapperInvoke {
    /**
     * 调用session前执行的开放接口，允许开发者自定义额外操作，列如：系统内置的多租户
     *
     * @param methodInfo mapper方法详尽信息
     * @param argMap     参数
     * @param type       mapper接口
     * @return
     */
    Object invoke(MethodInfo methodInfo, Map<String, Object> argMap, Class<?> type);
}
