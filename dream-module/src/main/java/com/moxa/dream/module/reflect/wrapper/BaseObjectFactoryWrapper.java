package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.BaseObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

public class BaseObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory(Object target) {
        return new BaseObjectFactory(target);
    }
}
