package com.moxa.dream.module.producer.wrapper;


import com.moxa.dream.module.producer.factory.ObjectFactory;
import com.moxa.dream.module.producer.util.NonCollection;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.util.*;

public interface ObjectFactoryWrapper {
    static ObjectFactoryWrapper wrapper(Class type) {
        if(NonCollection.class.isAssignableFrom(type)){
            return new NonCollectionObjectFactoryWrapper();
        }else if (List.class.isAssignableFrom(type)) {
            return new ListObjectFactoryWrapper(type);
        } else if(Set.class.isAssignableFrom(type)){
            return new SetObjectFactoryWrapper(type);
        }else if(Queue.class.isAssignableFrom(type)){
            return new QueueObjectFactoryWrapper(type);
        }else if (Map.class.isAssignableFrom(type)) {
            return new MapObjectFactoryWrapper();
        } else if(Collection.class.isAssignableFrom(type)){
            return new CollectionObjectFactoryWrapper(type);
        }else if (ReflectUtil.isBaseClass(type)) {
            return new BaseObjectFactoryWrapper(type);
        } else {
            return new BeanObjectFactoryWrapper(type);
        }
    }

    ObjectFactory newObjectFactory();
}
