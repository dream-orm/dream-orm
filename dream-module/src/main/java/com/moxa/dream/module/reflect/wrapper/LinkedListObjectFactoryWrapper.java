package com.moxa.dream.module.reflect.wrapper;


import com.moxa.dream.module.reflect.factory.LinkedListObjectFactory;
import com.moxa.dream.module.reflect.factory.ObjectFactory;

import java.util.LinkedList;

public class LinkedListObjectFactoryWrapper implements ObjectFactoryWrapper {

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new LinkedListObjectFactory(new LinkedList());
        } else {
            return new LinkedListObjectFactory((LinkedList) target);
        }
    }
}
