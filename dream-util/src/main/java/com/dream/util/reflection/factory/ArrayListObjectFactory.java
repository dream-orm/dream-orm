package com.dream.util.reflection.factory;


import java.util.ArrayList;

public class ArrayListObjectFactory extends CollectionObjectFactory {
    public ArrayListObjectFactory() {
        this(new ArrayList());
    }

    public ArrayListObjectFactory(ArrayList target) {
        super(target);
    }

}
