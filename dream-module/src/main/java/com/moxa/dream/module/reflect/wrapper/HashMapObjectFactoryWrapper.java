package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.HashMapObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

public class HashMapObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new HashMapObjectFactory();
    }
}
