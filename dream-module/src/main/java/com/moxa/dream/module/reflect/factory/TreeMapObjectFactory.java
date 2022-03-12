package com.moxa.dream.module.reflect.factory;


import com.moxa.dream.module.reflect.wrapper.PropertyInfo;

import java.util.TreeMap;

public class TreeMapObjectFactory extends HashMapObjectFactory {
    public TreeMapObjectFactory() {
        result = new TreeMap<>();
    }

    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        result.put(propertyInfo.getLabel(), value);
    }

    @Override
    public Object get(PropertyInfo propertyInfo) {
        return result.get(propertyInfo.getLabel());
    }

    @Override
    public Object getObject() {
        return result;
    }
}
