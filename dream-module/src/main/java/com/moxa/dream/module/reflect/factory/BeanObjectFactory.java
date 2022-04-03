package com.moxa.dream.module.reflect.factory;

import com.moxa.dream.module.reflect.wrapper.BeanObjectFactoryWrapper;

public class BeanObjectFactory implements ObjectFactory {

    protected BeanObjectFactoryWrapper factoryWrapper;
    Object result;

    public BeanObjectFactory(Class type, BeanObjectFactoryWrapper factoryWrapper) {
        result = newInstance(type);
        this.factoryWrapper = factoryWrapper;
    }

    public BeanObjectFactory(Object target, BeanObjectFactoryWrapper factoryWrapper) {
        result = target;
        this.factoryWrapper = factoryWrapper;
    }

    protected Object newInstance(Class<?> type) {
        try {
            return type.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalCallerException(e);
        }
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
