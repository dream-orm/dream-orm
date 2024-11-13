package com.dream.util.reflection.factory;

import java.lang.reflect.Array;

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
        if (property != null && Character.isDigit(property.charAt(0))) {
            int index = Integer.parseInt(property);
            int length = Array.getLength(target);
            if (index < length) {
                return Array.get(target, index);
            } else {
                return null;
            }
        } else {
            return target;
        }
    }

    @Override
    public Object getObject() {
        return target;
    }
}
