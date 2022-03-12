package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.LinkedHashSetObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

public class LinkedListObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new LinkedHashSetObjectFactory();
    }
}
