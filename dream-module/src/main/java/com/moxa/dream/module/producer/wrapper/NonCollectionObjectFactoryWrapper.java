package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.NonCollectionObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class NonCollectionObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory() {
        return new NonCollectionObjectFactory();
    }
}
