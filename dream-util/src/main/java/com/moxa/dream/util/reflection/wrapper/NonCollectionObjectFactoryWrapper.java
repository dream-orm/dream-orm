package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.reflection.factory.NonCollectionObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;
import com.moxa.dream.util.reflection.util.NonCollection;

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
