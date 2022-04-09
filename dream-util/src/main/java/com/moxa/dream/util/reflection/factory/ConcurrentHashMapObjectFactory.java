package com.moxa.dream.util.reflection.factory;


import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapObjectFactory extends MapObjectFactory {
    public ConcurrentHashMapObjectFactory() {
        super(new ConcurrentHashMap<>());
    }

    public ConcurrentHashMapObjectFactory(ConcurrentHashMap target) {
        super(target);
    }

}
