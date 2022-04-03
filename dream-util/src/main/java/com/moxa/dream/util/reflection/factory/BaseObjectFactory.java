package com.moxa.dream.util.reflection.factory;


public class BaseObjectFactory implements ObjectFactory {
    private Object result;

    public BaseObjectFactory(Object target) {
        result = target;
    }

    @Override
    public void set(String property, Object value) {
        this.result = value;
    }

    @Override
    public Object get(String property) {
        return null;
    }

    @Override
    public Object getObject() {
        return result;
    }
}
