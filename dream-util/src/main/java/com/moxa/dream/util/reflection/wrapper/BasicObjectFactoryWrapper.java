package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.reflection.factory.BasicObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

public class BasicObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory(Object target) {
        return new BasicObjectFactory(target);
    }
}
