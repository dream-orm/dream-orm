package com.moxa.dream.module.reflect.factory;

import com.moxa.dream.module.reflect.wrapper.PropertyInfo;


public interface ObjectFactory {

    void set(PropertyInfo propertyInfo, Object value);

    Object get(PropertyInfo propertyInfo);

    Object getObject();
}
