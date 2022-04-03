package com.moxa.dream.module.reflect.factory;

import java.util.TreeSet;

public class TreeSetObjectFactory extends CollectionObjectFactory {

    public TreeSetObjectFactory() {
        this(new TreeSet());
    }

    public TreeSetObjectFactory(TreeSet treeSet) {
        super(treeSet, null);
    }

}
