package com.moxa.dream.module.producer.factory;

import com.moxa.dream.module.producer.PropertyInfo;

import java.util.Collection;

public class CollectionObjectFactory extends BeanObjectFactory {
    Collection result;
    public CollectionObjectFactory(){

    }
    public CollectionObjectFactory(Class<? extends Collection> type) {
        super(type);
    }
    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        if(propertyInfo==null)
            result.add(value);
        else{
            super.set(propertyInfo, value);
        }
    }
}
