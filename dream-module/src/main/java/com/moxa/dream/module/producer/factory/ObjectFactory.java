package com.moxa.dream.module.producer.factory;

import com.moxa.dream.module.producer.PropertyInfo;

import java.util.Collection;
import java.util.Map;

public interface ObjectFactory {

    void set(PropertyInfo propertyInfo, Object value);

    Object get(PropertyInfo propertyInfo);

    Object getObject();

    static ObjectFactory of(Object result) {
        if (result instanceof Collection) {
            CollectionObjectFactory objectFactory = new CollectionObjectFactory();
            objectFactory.result = (Collection) result;
            return objectFactory;
        } else if (result instanceof Map) {
            MapObjectFactory objectFactory = new MapObjectFactory();
            objectFactory.result = (Map<String, Object>) result;
            return objectFactory;
        } else {
            BeanObjectFactory objectFactory = new BeanObjectFactory();
            objectFactory.result = result;
            return objectFactory;
        }
    }
}
