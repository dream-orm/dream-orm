package com.moxa.dream.module.producer.factory;


import com.moxa.dream.module.producer.PropertyInfo;
import com.moxa.dream.module.producer.util.NonCollection;


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
