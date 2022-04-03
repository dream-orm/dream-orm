package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.CollectionObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

import java.util.Collection;

public class CollectionObjectFactoryWrapper extends BeanObjectFactoryWrapper {
    public CollectionObjectFactoryWrapper(Class type) {
        super(type);
    }

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new CollectionObjectFactory(type, null);
        } else {
            return new CollectionObjectFactory((Collection) target, null);
        }
    }
}
