package com.moxa.dream.util.reflection.factory;


import java.util.ArrayDeque;

public class ArrayDequeObjectFactory extends CollectionObjectFactory {
    public ArrayDequeObjectFactory() {
        this(new ArrayDeque());
    }

    public ArrayDequeObjectFactory(ArrayDeque target) {
        super(target);
    }
}
