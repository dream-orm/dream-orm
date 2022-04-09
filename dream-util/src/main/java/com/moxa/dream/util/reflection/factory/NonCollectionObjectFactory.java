package com.moxa.dream.util.reflection.factory;


import com.moxa.dream.util.reflection.util.NonCollection;


public class NonCollectionObjectFactory implements ObjectFactory {
    private NonCollection result;

    public NonCollectionObjectFactory() {
        this(new NonCollection());
    }

    public NonCollectionObjectFactory(NonCollection target) {
        this.result = target;
    }

    @Override
    public void set(String property, Object value) {
        result.add(value);
    }

    @Override
    public Object get(String property) {
        return result.getObject();
    }

    @Override
    public Object getObject() {
        return result.getObject();
    }
}
