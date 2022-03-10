package com.moxa.dream.module.producer.wrapper;

import com.moxa.dream.module.producer.factory.BeanObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class BeanObjectFactoryWrapper implements ObjectFactoryWrapper {
    private Class type;
    public BeanObjectFactoryWrapper(Class type) {
        this.type=type;
    }

    @Override
    public ObjectFactory newObjectFactory() {
        return new BeanObjectFactory(type);
    }
}
