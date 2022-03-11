package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.HashSetObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class HashSetObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory() {
        return new HashSetObjectFactory();
    }
}
