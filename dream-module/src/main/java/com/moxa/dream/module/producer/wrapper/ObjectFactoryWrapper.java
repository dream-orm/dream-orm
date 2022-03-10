package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.ObjectFactory;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.util.Collection;
import java.util.Map;

public interface ObjectFactoryWrapper {
    ObjectFactory newObjectFactory();

   static  ObjectFactoryWrapper wrapper(Class type) {
        if (Collection.class.isAssignableFrom(type)) {
            return new CollectionObjectFactoryWrapper(type);
        } else if (Map.class.isAssignableFrom(type)) {
            return new MapObjectFactoryWrapper();
        } else if (ReflectUtil.isBaseClass(type)) {
            return new BaseObjectFactoryWrapper(type);
        } else {
            return new BeanObjectFactoryWrapper(type);
        }
    }
}
