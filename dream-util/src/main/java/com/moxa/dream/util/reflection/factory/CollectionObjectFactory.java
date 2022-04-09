package com.moxa.dream.util.reflection.factory;

import com.moxa.dream.util.reflection.wrapper.BeanObjectFactoryWrapper;

import java.util.Collection;

public class CollectionObjectFactory extends BeanObjectFactory {
    public CollectionObjectFactory(Collection target) {
        this(target, null);
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
