package com.dream.util.reflection.wrapper;


import com.dream.util.reflection.factory.ArrayListObjectFactory;
import com.dream.util.reflection.factory.ObjectFactory;

import java.util.ArrayList;

public class ArrayListObjectFactoryWrapper implements ObjectFactoryWrapper {
    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new ArrayListObjectFactory();
        } else {
            return new ArrayListObjectFactory((ArrayList) target);
        }
    }
}
