package com.moxa.dream.system.mapper.factory;

import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.mapper.handler.MapperHandler;

import java.util.Collection;


public interface MapperFactory {

    void addMapper(Configuration configuration, Class type);

    <T> T getMapper(Class<T> type, MapperHandler mapperHandler);

    Collection<Class> getMapperTypeList();
}
