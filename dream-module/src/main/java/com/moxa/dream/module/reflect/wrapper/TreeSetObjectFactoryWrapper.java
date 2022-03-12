package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.ObjectFactory;
import com.moxa.dream.module.reflect.factory.TreeSetObjectFactory;

public class TreeSetObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory() {
        return new TreeSetObjectFactory();
    }
}
