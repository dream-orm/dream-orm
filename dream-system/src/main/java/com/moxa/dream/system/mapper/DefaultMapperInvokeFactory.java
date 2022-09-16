package com.moxa.dream.system.mapper;

import com.moxa.dream.system.core.session.Session;

public class DefaultMapperInvokeFactory implements MapperInvokeFactory {
    @Override
    public MapperInvoke getMapperInvoke(Session session) {
        return new DefaultMapperInvoke(session);
    }
}
