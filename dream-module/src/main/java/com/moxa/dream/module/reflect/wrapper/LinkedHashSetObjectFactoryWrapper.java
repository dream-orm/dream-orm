package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.LinkedHashSetObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

import java.util.LinkedHashSet;

public class LinkedHashSetObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new LinkedHashSetObjectFactory(new LinkedHashSet());
        } else {
            return new LinkedHashSetObjectFactory((LinkedHashSet) target);
        }
    }
}
