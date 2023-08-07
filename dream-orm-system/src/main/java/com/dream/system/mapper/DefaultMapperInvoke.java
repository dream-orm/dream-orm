package com.dream.system.mapper;

import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;

import java.util.Map;

public class DefaultMapperInvoke implements MapperInvoke {
    private Session session;

    public DefaultMapperInvoke(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(MethodInfo methodInfo, Map<String, Object> argMap, Class<?> type) {
        return session.execute(methodInfo, argMap);
    }
}
