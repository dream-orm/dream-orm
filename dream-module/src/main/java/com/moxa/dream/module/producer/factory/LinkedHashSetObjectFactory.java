package com.moxa.dream.module.producer.factory;


import java.util.LinkedHashSet;
import java.util.LinkedList;

public class LinkedHashSetObjectFactory extends CollectionObjectFactory {
    public LinkedHashSetObjectFactory() {
        result = new LinkedHashSet();
    }
}
