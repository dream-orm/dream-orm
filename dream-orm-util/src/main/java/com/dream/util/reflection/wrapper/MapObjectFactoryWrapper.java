package com.dream.util.reflection.wrapper;


import com.dream.util.reflect.ReflectUtil;
import com.dream.util.reflection.factory.MapObjectFactory;
import com.dream.util.reflection.factory.ObjectFactory;

import java.util.Map;

public class MapObjectFactoryWrapper implements ObjectFactoryWrapper {
    private Class<? extends Map> type;

    public MapObjectFactoryWrapper(Class<? extends Map> type) {
        this.type = type;
    }

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new MapObjectFactory(ReflectUtil.create(type));
        } else {
            return new MapObjectFactory((Map) target);
        }
    }
}
