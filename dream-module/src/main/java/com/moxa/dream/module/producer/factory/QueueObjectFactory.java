package com.moxa.dream.module.producer.factory;


import com.moxa.dream.module.producer.PropertyInfo;
import java.util.*;

public class QueueObjectFactory extends CollectionObjectFactory {
    public QueueObjectFactory(Class<? extends Collection> type) {
        if (type.isAssignableFrom(ArrayDeque.class)) {
            result = new ArrayDeque();
        } else{
            result=newInstance(type);
        }
    }

    @Override
    public void set(PropertyInfo propertyInfo, Object value) {
        result.add(value);
    }

    @Override
    public Object get(PropertyInfo propertyInfo) {
     return super.get(propertyInfo);
    }

    @Override
    public Object getObject() {
        return result;
    }
}
