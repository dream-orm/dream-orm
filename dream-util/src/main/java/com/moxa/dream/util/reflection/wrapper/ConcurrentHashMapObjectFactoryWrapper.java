package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.reflection.factory.ConcurrentHashMapObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new ConcurrentHashMapObjectFactory(new ConcurrentHashMap(4));
        } else {
            return new ConcurrentHashMapObjectFactory((ConcurrentHashMap) target);
        }
    }
}
