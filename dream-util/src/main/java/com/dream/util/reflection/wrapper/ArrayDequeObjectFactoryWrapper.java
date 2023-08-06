package com.dream.util.reflection.wrapper;


import com.dream.util.reflection.factory.ArrayDequeObjectFactory;
import com.dream.util.reflection.factory.ObjectFactory;

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
