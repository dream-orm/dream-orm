package com.moxa.dream.module.reflect.factory;


import com.moxa.dream.module.reflect.util.NonCollection;
import com.moxa.dream.module.reflect.wrapper.PropertyInfo;


public class NonCollectionObjectFactory extends CollectionObjectFactory {
    public NonCollectionObjectFactory() {
        result = new NonCollection();
    }

    @Override
    public Object get(PropertyInfo propertyInfo) {
        return get(getObject(), propertyInfo);
    }

    @Override
    public Object getObject() {
        return ((NonCollection) result).getObject();
    }
}
