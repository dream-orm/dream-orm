package com.moxa.dream.util.reflection.factory;


import java.util.Map;

public class MapObjectFactory implements ObjectFactory {
    private final Map result;

    public MapObjectFactory(Map<String, Object> target) {
        this.result = target;
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
