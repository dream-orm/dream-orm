package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.ObjectFactory;
import com.moxa.dream.module.producer.factory.QueueObjectFactory;

public class QueueObjectFactoryWrapper implements ObjectFactoryWrapper {
    private Class type;

    public QueueObjectFactoryWrapper(Class type) {
        this.type = type;
    }

    @Override
    public ObjectFactory newObjectFactory() {
        return new QueueObjectFactory(type);
    }
}
