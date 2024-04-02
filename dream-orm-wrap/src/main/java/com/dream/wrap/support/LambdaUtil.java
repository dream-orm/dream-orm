package com.dream.wrap.support;


import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LambdaUtil {

    private static final Map<Class<?>, SerializedLambda> lambdaMap = new ConcurrentHashMap<>();
    private static final Map<String, Class<?>> classMap = new ConcurrentHashMap<>();
    private LambdaUtil() {
    }

    public static <T> String getFieldName(LambdaGetter<T> getter) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = getter.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        SerializedLambda serializedLambda = (SerializedLambda) method.invoke(getter);
        return null;
    }
}
