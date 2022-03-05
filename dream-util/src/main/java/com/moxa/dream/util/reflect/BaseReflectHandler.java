package com.moxa.dream.util.reflect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseReflectHandler<T> implements ReflectHandler<T> {

    @Override
    public List<Class> goHandler(Class type) {
        List<Class> list = new ArrayList();
        Class[] interfaces = type.getInterfaces();
        if (interfaces != null && interfaces.length > 0)
            list.addAll(Arrays.asList(interfaces));
        Class superclass = type.getSuperclass();
        if (superclass != null && superclass != Object.class)
            list.add(superclass);
        return list;
    }
}
