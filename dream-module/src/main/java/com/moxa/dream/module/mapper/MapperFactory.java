package com.moxa.dream.module.mapper;

import com.moxa.dream.module.config.Configuration;

import java.util.Collection;


public interface MapperFactory {

    void addMapper(Configuration configuration, Class type);

    <T> T getMapper(Class<T> type, MapperHandler mapperHandler);

    Collection<Class> getMapperTypeList();
}
