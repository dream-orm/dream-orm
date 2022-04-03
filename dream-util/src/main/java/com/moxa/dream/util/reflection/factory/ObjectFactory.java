package com.moxa.dream.util.reflection.factory;


public interface ObjectFactory {

    void set(String property, Object value);

    Object get(String property);

    Object getObject();
}
