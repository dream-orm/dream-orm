package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.NonCollectionObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;
import com.moxa.dream.module.reflect.util.NonCollection;

public class NonCollectionObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new NonCollectionObjectFactory(new NonCollection());

        } else {
            return new NonCollectionObjectFactory((NonCollection) target);
        }
    }
}
