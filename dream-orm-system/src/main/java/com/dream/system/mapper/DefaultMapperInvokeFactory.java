package com.dream.system.mapper;

import com.dream.system.core.session.Session;

public class DefaultMapperInvokeFactory implements MapperInvokeFactory {
    @Override
    public MapperInvoke getMapperInvoke(Session session) {
        return new DefaultMapperInvoke(session);
    }
}
