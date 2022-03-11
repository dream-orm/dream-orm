package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.ArrayDequeObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class ArrayDequeObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new ArrayDequeObjectFactory();
    }
}
