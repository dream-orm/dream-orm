package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.CollectionObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class CollectionObjectFactoryWrapper implements ObjectFactoryWrapper {
    private Class type;

    public CollectionObjectFactoryWrapper(Class type) {
        this.type = type;
    }

    @Override
    public ObjectFactory newObjectFactory() {
        return new CollectionObjectFactory(type);
    }
}
