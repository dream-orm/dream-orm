package com.moxa.dream.module.producer.factory;


import java.util.ArrayDeque;

public class ArrayDequeObjectFactory extends CollectionObjectFactory {

    public ArrayDequeObjectFactory() {
        result = new ArrayDeque();
    }
}
