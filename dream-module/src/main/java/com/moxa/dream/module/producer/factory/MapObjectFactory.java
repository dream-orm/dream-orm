package com.moxa.dream.module.producer.factory;


import com.moxa.dream.module.producer.PropertyInfo;

import java.util.HashMap;
import java.util.Map;

public class MapObjectFactory implements ObjectFactory{
    Map<String,Object> result=new HashMap<>();
    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        result.put(propertyInfo.getLabel(),value);
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
