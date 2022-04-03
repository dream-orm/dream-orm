package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.reflection.factory.LinkedListObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

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
