package com.dream.util.reflection.factory;


import java.util.LinkedList;

public class LinkedListObjectFactory extends CollectionObjectFactory {
    public LinkedListObjectFactory() {
        this(new LinkedList());
    }

    public LinkedListObjectFactory(LinkedList target) {
        super(target);
    }

}
