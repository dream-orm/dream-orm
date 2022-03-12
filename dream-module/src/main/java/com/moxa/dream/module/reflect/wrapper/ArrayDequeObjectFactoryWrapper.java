package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.ArrayDequeObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

public class ArrayDequeObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new ArrayDequeObjectFactory();
    }
}
