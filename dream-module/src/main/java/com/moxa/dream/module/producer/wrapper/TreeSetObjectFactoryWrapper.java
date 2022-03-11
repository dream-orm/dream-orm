package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.ObjectFactory;
import com.moxa.dream.module.producer.factory.TreeSetObjectFactory;

public class TreeSetObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory() {
        return new TreeSetObjectFactory();
    }
}
