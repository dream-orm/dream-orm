package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.CollectionObjectFactory;
import com.moxa.dream.module.producer.factory.ListObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class ListObjectFactoryWrapper implements ObjectFactoryWrapper {
    private Class type;

    public ListObjectFactoryWrapper(Class type) {
        this.type = type;
    }

    @Override
    public ObjectFactory newObjectFactory() {
        return new ListObjectFactory(type);
    }
}
