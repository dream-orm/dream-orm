package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.ArrayListObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

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
