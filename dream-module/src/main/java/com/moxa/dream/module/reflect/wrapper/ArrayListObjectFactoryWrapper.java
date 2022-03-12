package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.ArrayListObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

public class ArrayListObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new ArrayListObjectFactory();
    }
}
