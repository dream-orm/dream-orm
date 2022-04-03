package com.moxa.dream.module.reflect.factory;


import java.util.TreeMap;

public class TreeMapObjectFactory extends HashMapObjectFactory {
    public TreeMapObjectFactory() {
        super(new TreeMap<>());
    }

    public TreeMapObjectFactory(TreeMap treeMap) {
        super(treeMap);
    }

}
