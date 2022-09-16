package com.moxa.dream.system.mapper;

import com.moxa.dream.system.core.session.Session;

public interface MapperInvokeFactory {
    MapperInvoke getMapperInvoke(Session session);
}
