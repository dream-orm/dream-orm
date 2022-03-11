package com.moxa.dream.module.producer.factory;

import java.util.Collection;
import java.util.HashSet;

public class SetObjectFactory extends CollectionObjectFactory {

    public SetObjectFactory(Class<? extends Collection> type) {
        if (type.isAssignableFrom(HashSet.class)) {
            result = new HashSet<>();
        }else{
          result=newInstance(type);
      }
    }
}
