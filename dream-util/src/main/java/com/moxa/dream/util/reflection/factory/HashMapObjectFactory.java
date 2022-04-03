package com.moxa.dream.util.reflection.factory;


import java.util.HashMap;
import java.util.Map;

public class HashMapObjectFactory implements ObjectFactory {
    Map<String, Object> result;

    public HashMapObjectFactory() {
        this(new HashMap<>());
    }

    public HashMapObjectFactory(Map<String, Object> target) {
        result = target;
    }

    @Override
    public void set(String property, Object value) {
        result.put(property, value);
    }

    @Override
    public Object get(String property) {
        return result.get(property);
    }

    @Override
    public Object getObject() {
        return result;
    }
}
