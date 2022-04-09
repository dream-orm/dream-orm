package com.moxa.dream.util.reflection.factory;

import com.moxa.dream.util.reflection.wrapper.BeanObjectFactoryWrapper;

public class BeanObjectFactory implements ObjectFactory {

    protected BeanObjectFactoryWrapper factoryWrapper;
    Object result;

    public BeanObjectFactory(Object target, BeanObjectFactoryWrapper factoryWrapper) {
        result = target;
        this.factoryWrapper = factoryWrapper;
    }

    @Override
    public void set(String property, Object value) {
        factoryWrapper.set(result, property, value);
    }

    @Override
    public Object get(String property) {
        return get(result, property);
    }

    protected Object get(Object result, String property) {
        return factoryWrapper.get(result, property);
    }

    @Override
    public Object getObject() {
        return result;
    }
}
