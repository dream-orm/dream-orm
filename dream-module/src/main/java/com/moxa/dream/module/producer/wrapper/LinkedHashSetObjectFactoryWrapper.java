package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.LinkedHashSetObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class LinkedHashSetObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new LinkedHashSetObjectFactory();
    }
}
