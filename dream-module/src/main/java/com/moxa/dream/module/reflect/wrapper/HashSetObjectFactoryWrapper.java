package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.HashSetObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

public class HashSetObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory() {
        return new HashSetObjectFactory();
    }
}
