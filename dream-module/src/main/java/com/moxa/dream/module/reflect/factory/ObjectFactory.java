package com.moxa.dream.module.reflect.factory;


public interface ObjectFactory {

    void set(String property, Object value);

    Object get(String property);

    Object getObject();
}
