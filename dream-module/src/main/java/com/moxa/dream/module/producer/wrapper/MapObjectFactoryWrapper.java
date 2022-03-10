package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.MapObjectFactory;
import com.moxa.dream.module.producer.factory.ObjectFactory;

public class MapObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory() {
        return new MapObjectFactory();
    }
}
