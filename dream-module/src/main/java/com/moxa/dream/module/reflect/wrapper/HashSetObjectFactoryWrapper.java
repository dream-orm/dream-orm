package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.HashSetObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

import java.util.HashSet;

public class HashSetObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new HashSetObjectFactory(new HashSet());
        } else {
            return new HashSetObjectFactory((HashSet) target);
        }
    }
}
