package com.moxa.dream.util.reflection.wrapper;


import com.moxa.dream.util.reflect.ReflectUtil;
import com.moxa.dream.util.reflection.factory.MapObjectFactory;
import com.moxa.dream.util.reflection.factory.ObjectFactory;

import java.util.Map;

public class MapObjectFactoryWrapper extends BeanObjectFactoryWrapper {
    public MapObjectFactoryWrapper(Class type) {
        super(type);
    }

    @Override
    public ObjectFactory newObjectFactory(Object target) {
        if (target == null) {
            return new MapObjectFactory((Map<String, Object>) ReflectUtil.create(type));
        } else {
            return new MapObjectFactory((Map) target);
        }
    }
}
