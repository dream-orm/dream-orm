package com.moxa.dream.module.reflect.factory;


import java.util.LinkedList;

public class LinkedListObjectFactory extends CollectionObjectFactory {
    public LinkedListObjectFactory() {
        this(new LinkedList());
    }

    public LinkedListObjectFactory(LinkedList target) {
        super(target, null);
    }

}
