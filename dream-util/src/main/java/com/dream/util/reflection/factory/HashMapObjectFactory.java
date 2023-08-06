package com.dream.util.reflection.factory;


import java.util.HashMap;

public class HashMapObjectFactory extends MapObjectFactory {
    public HashMapObjectFactory() {
        super(new HashMap<>(8));
    }

    public HashMapObjectFactory(HashMap<String, Object> target) {
        super(target);
    }
}
