package com.moxa.dream.module.producer.factory;



import java.util.*;

public class ListObjectFactory extends CollectionObjectFactory {

    ListObjectFactory() {

    }
    public ListObjectFactory(Class<? extends List> type) {
        if (type.isAssignableFrom(ArrayList.class)) {
            result = new ArrayList();
        }else result=newInstance(type);
    }
}
