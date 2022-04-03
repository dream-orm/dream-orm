package com.moxa.dream.module.reflect.factory;

import java.util.HashSet;

public class HashSetObjectFactory extends CollectionObjectFactory {

    public HashSetObjectFactory() {
        this(new HashSet());
    }

    public HashSetObjectFactory(HashSet target) {
        super(target, null);
    }
}
