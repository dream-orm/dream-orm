package com.moxa.dream.module.hold.mapper;

import com.moxa.dream.module.hold.config.Configuration;

import java.util.Collection;


public interface MapperFactory {

    void addMapper(Configuration configuration, Class type);

    <T> T getMapper(Class<T> type, MapperHandler mapperHandler);

    Collection<Class> getMapperTypeList();
}
