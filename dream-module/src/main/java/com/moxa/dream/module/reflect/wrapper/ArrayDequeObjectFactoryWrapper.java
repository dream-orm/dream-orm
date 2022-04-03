package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.ArrayDequeObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

import java.util.ArrayDeque;

public class ArrayDequeObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new ArrayDequeObjectFactory();
        } else {
            return new ArrayDequeObjectFactory((ArrayDeque) target);
        }
    }
}
