package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.BaseObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class BaseObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new BaseObjectFactory();
    }
}
