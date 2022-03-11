package com.moxa.dream.module.producer.factory;

import com.moxa.dream.module.producer.PropertyInfo;

import java.util.Collection;
import java.util.Map;

public interface ObjectFactory {

    void set(PropertyInfo propertyInfo, Object value);

    Object get(PropertyInfo propertyInfo);

    Object getObject();
}
