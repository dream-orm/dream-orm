package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.NonCollectionObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

public class NonCollectionObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory() {
        return new NonCollectionObjectFactory();
    }
}
