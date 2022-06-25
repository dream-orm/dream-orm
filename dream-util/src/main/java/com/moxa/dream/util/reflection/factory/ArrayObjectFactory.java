package com.moxa.dream.util.reflection.factory;

public class ArrayObjectFactory implements ObjectFactory {
    private Object target;

    public ArrayObjectFactory() {
        this(new Object[0]);
    }

    public ArrayObjectFactory(Object target) {
        this.target = target;
    }

    @Override
    public void set(String property, Object value) {
        throw new RuntimeException("unSupport set");
    }

    @Override
    public Object get(String property) {
        return target;
    }

    @Override
    public Object getObject() {
        return target;
    }
}
