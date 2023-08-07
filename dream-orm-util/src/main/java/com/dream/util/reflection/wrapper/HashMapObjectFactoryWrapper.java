package com.dream.util.reflection.wrapper;


import com.dream.util.reflection.factory.HashMapObjectFactory;
import com.dream.util.reflection.factory.ObjectFactory;

import java.util.HashMap;

public class HashMapObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new HashMapObjectFactory(new HashMap<>(8));
        } else {
            return new HashMapObjectFactory((HashMap) target);
        }
    }
}
