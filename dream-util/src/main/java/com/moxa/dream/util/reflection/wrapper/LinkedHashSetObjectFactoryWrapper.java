package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.reflection.factory.LinkedHashSetObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

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
