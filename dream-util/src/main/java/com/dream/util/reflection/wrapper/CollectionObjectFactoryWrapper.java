package com.dream.util.reflection.wrapper;


import com.dream.util.reflect.ReflectUtil;
import com.dream.util.reflection.factory.CollectionObjectFactory;
import com.dream.util.reflection.factory.ObjectFactory;

import java.util.Collection;

public class CollectionObjectFactoryWrapper implements ObjectFactoryWrapper {
    protected Class<? extends Collection> type;

    public CollectionObjectFactoryWrapper(Class<? extends Collection> type) {
        this.type = type;
    }

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new CollectionObjectFactory(ReflectUtil.create(type), this);
        } else {
            return new CollectionObjectFactory((Collection) target, this);
        }
    }
}
