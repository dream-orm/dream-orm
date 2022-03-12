package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.ObjectFactory;
import com.moxa.dream.module.reflect.factory.TreeMapObjectFactory;

public class TreeMapObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory() {
        return new TreeMapObjectFactory();
    }
}
