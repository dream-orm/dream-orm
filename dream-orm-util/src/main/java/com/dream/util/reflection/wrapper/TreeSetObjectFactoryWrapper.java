package com.dream.util.reflection.wrapper;


import com.dream.util.reflection.factory.ObjectFactory;
import com.dream.util.reflection.factory.TreeSetObjectFactory;

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
