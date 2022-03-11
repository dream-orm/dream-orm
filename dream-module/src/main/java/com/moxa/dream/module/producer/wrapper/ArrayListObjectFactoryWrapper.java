package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.ArrayListObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class ArrayListObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new ArrayListObjectFactory();
    }
}
