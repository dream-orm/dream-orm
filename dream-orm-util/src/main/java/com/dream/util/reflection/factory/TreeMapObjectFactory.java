package com.dream.util.reflection.factory;


import java.util.TreeMap;

public class TreeMapObjectFactory extends MapObjectFactory {
    public TreeMapObjectFactory() {
        super(new TreeMap<>());
    }

    public TreeMapObjectFactory(TreeMap treeMap) {
        super(treeMap);
    }

}
