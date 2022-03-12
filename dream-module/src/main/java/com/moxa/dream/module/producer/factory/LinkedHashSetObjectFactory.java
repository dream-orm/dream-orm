package com.moxa.dream.module.producer.factory;


import java.util.LinkedHashSet;

public class LinkedHashSetObjectFactory extends CollectionObjectFactory {
    public LinkedHashSetObjectFactory() {
        result = new LinkedHashSet();
    }
}
