package com.moxa.dream.util.reflect;

import java.util.List;

public interface ReflectHandler<T> {
    List<T> doHandler(Class type);

    List<Class> goHandler(Class type);
}
