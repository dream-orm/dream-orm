package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.ObjectFactory;
import com.moxa.dream.module.reflect.factory.TreeSetObjectFactory;

import java.util.TreeSet;

public class TreeSetObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new TreeSetObjectFactory(new TreeSet());
        } else {
            return new TreeSetObjectFactory((TreeSet) target);
        }
    }
}
