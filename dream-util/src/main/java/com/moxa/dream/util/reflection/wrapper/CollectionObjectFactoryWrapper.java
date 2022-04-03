package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.reflection.factory.CollectionObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

import java.util.Collection;

public class CollectionObjectFactoryWrapper extends BeanObjectFactoryWrapper {
    public CollectionObjectFactoryWrapper(Class type) {
        super(type);
    }

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new CollectionObjectFactory(type, this);
        } else {
            return new CollectionObjectFactory((Collection) target, this);
        }
    }
}
