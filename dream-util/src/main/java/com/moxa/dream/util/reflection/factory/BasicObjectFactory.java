package com.moxa.dream.util.reflection.factory;


public class BasicObjectFactory implements ObjectFactory {
    private Object result;

    public BasicObjectFactory(Object target) {
        result = target;
    }

    @Override
    public void set(String property, Object value) {
        this.result = value;
    }

    @Override
    public Object get(String property) {
        return result;
    }

    @Override
    public Object getObject() {
        return result;
    }
}
