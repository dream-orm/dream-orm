package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.reflection.factory.HashSetObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

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
