package com.dream.util.reflect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class FieldReflectHandler implements ReflectHandler<Field> {
    @Override
    public List<Field> doHandler(Class type) {
        return Arrays.asList(type.getDeclaredFields());
    }
}
