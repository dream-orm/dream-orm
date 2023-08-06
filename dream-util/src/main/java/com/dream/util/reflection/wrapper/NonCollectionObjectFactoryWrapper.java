package com.dream.util.reflection.wrapper;


import com.dream.util.common.NonCollection;
import com.dream.util.reflection.factory.NonCollectionObjectFactory;
import com.dream.util.reflection.factory.ObjectFactory;

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
