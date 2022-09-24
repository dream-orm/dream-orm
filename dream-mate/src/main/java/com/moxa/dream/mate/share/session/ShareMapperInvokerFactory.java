package com.moxa.dream.mate.share.session;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.mapper.MapperInvoke;
import com.moxa.dream.system.mapper.MapperInvokeFactory;

public class ShareMapperInvokerFactory implements MapperInvokeFactory {
    @Override
    public MapperInvoke getMapperInvoke(Session session) {
        return new ShareMapperInvoke(session);
    }
}
