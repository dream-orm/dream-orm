package com.moxa.dream.module.reflect.factory;


import com.moxa.dream.module.reflect.util.NonCollection;


public class NonCollectionObjectFactory extends CollectionObjectFactory {
    public NonCollectionObjectFactory() {
        this(new NonCollection());
    }

    public NonCollectionObjectFactory(NonCollection nonCollection) {
        super(nonCollection, null);
    }

    @Override
    public Object get(String property) {
        return get(getObject(), property);
    }

    @Override
    public Object getObject() {
        return ((NonCollection) result).getObject();
    }
}
