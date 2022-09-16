package com.moxa.dream.system.mapper;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapped.MethodInfo;

public class DefaultMapperInvoke implements MapperInvoke {
    private Session session;

    public DefaultMapperInvoke(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(MethodInfo methodInfo, Object arg) {
        return session.execute(methodInfo, arg);
    }
}
