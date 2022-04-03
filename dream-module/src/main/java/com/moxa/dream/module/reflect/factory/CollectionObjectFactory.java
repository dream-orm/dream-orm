package com.moxa.dream.module.reflect.factory;

import com.moxa.dream.module.reflect.wrapper.BeanObjectFactoryWrapper;

import java.util.Collection;

public class CollectionObjectFactory extends BeanObjectFactory {
    public CollectionObjectFactory(Class<? extends Collection> type) {
        this(type, null);
    }

    public CollectionObjectFactory(Collection target) {
        this(target, null);
    }

    public CollectionObjectFactory(Class<? extends Collection> type, BeanObjectFactoryWrapper factoryWrapper) {
        super(type, factoryWrapper);
    }

    public CollectionObjectFactory(Collection target, BeanObjectFactoryWrapper factoryWrapper) {
        super(target, factoryWrapper);
    }

    @Override
    public void set(String property, Object value) {
        if (property == null)
            ((Collection) result).add(value);
        else {
            super.set(property, value);
        }
    }
}
