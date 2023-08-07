package com.dream.util.reflection.wrapper;


import com.dream.util.reflection.factory.ArrayObjectFactory;
import com.dream.util.reflection.factory.ObjectFactory;


public class ArrayObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new ArrayObjectFactory();
        } else {
            return new ArrayObjectFactory(target);
        }
    }
}
