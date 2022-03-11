package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.ListObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;
import com.moxa.dream.module.producer.factory.SetObjectFactory;

public class SetObjectFactoryWrapper implements ObjectFactoryWrapper {
    private Class type;

    public SetObjectFactoryWrapper(Class type) {
        this.type = type;
    }

    @Override
    public ObjectFactory newObjectFactory() {
        return new SetObjectFactory(type);
    }
}
