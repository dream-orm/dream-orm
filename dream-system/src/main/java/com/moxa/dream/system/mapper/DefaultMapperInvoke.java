package com.moxa.dream.system.mapper;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapped.MethodInfo;

import java.util.Map;

public class DefaultMapperInvoke implements MapperInvoke {
    private Session session;

    public DefaultMapperInvoke(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(MethodInfo methodInfo, Map<String, Object> argMap) {
        return session.execute(methodInfo, argMap);
    }
}
