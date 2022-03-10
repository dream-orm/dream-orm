package com.moxa.dream.module.producer.factory;


import com.moxa.dream.module.producer.PropertyInfo;

import java.util.HashMap;
import java.util.Map;

public class MapObjectFactory implements ObjectFactory{
    private Map<String,Object> map=new HashMap<>();
    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        map.put(propertyInfo.getLabel(),value);
    }

    @Override
    public Object getObject() {
        return map;
    }
}
