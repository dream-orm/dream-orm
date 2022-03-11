package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.ObjectFactory;
import com.moxa.dream.module.producer.factory.TreeMapObjectFactory;

public class TreeMapObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory() {
        return new TreeMapObjectFactory();
    }
}
