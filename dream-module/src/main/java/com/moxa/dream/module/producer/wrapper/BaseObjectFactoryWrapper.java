package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.BaseObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class BaseObjectFactoryWrapper implements ObjectFactoryWrapper {
    private Class type;

    public BaseObjectFactoryWrapper(Class type) {
        this.type = type;
    }

    @Override
    public ObjectFactory newObjectFactory() {
        return new BaseObjectFactory(type);
    }
}
