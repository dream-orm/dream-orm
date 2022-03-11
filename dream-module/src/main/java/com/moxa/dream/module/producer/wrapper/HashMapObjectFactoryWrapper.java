package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.HashMapObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class HashMapObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new HashMapObjectFactory();
    }
}
