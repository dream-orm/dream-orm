package com.dream.mate.share.session;

import com.dream.system.core.session.Session;
import com.dream.system.mapper.MapperInvoke;
import com.dream.system.mapper.MapperInvokeFactory;

public class ShareMapperInvokerFactory implements MapperInvokeFactory {
    @Override
    public MapperInvoke getMapperInvoke(Session session) {
        return new ShareMapperInvoke(session);
    }
}
