package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.HashMapObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

import java.util.HashMap;

public class HashMapObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new HashMapObjectFactory(new HashMap<>());
        } else {
            return new HashMapObjectFactory((HashMap) target);
        }
    }
}
