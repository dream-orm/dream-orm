package com.dream.util.reflection.wrapper;


import com.dream.util.reflection.factory.ObjectFactory;
import com.dream.util.reflection.factory.TreeMapObjectFactory;

import java.util.TreeMap;

public class TreeMapObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new TreeMapObjectFactory(new TreeMap());
        } else {
            return new TreeMapObjectFactory((TreeMap) target);
        }
    }
}
