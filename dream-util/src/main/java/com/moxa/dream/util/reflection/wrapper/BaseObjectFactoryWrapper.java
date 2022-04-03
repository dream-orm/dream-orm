package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.reflection.factory.BaseObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

public class BaseObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory(Object target) {
        return new BaseObjectFactory(target);
    }
}
