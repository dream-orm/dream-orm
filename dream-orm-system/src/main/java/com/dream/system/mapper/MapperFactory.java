package com.dream.system.mapper;

import com.dream.system.config.Configuration;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;

import java.util.Collection;


public interface MapperFactory {

    void addMethodInfo(MethodInfo methodInfo);

    MethodInfo getMethodInfo(String id);

    boolean addMapper(Configuration configuration, Class<?> type);

    <T> T getMapper(Class<T> type, Session session);

    Collection<Class> getMapperTypeList();
}
