package com.moxa.dream.util.reflect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class MethodReflectHandler implements ReflectHandler<Method> {
    @Override
    public List<Method> doHandler(Class type) {
        return Arrays.asList(type.getDeclaredMethods());
    }
}
