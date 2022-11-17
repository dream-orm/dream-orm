package com.moxa.dream.system.mapper;

import com.moxa.dream.system.config.Configuration;

import java.util.Collection;


public interface MapperFactory {

    boolean addMapper(Configuration configuration, Class type);

    <T> T getMapper(Class<T> type, MapperInvoke mapperInvoke);

    Collection<Class> getMapperTypeList();
}
