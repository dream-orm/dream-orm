package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.reflection.factory.NonCollectionObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

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
