package com.dream.util.reflection.factory;


import java.util.LinkedHashSet;

public class LinkedHashSetObjectFactory extends CollectionObjectFactory {
    public LinkedHashSetObjectFactory() {
        this(new LinkedHashSet());
    }

    public LinkedHashSetObjectFactory(LinkedHashSet target) {
        super(target);
    }

}
