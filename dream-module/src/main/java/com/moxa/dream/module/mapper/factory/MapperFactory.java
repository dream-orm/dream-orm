package com.moxa.dream.module.mapper.factory;

import com.moxa.dream.module.config.Configuration;
import com.moxa.dream.module.mapper.handler.MapperHandler;

import java.util.Collection;


public interface MapperFactory {

    void addMapper(Configuration configuration, Class type);

    <T> T getMapper(Class<T> type, MapperHandler mapperHandler);

    Collection<Class> getMapperTypeList();
}
