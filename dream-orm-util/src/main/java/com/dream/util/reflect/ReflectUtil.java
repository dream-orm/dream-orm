package com.dream.util.reflect;

import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectUtil {
    private static ReflectLoader reflectLoader = new ReflectLoader();

    public static <T> T create(Class<T> type) {
        try {
            return type.getConstructor().newInstance();
        } catch (Exception e) {
            try {
                Constructor<T> constructor = type.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (Exception exception) {
                throw new DreamRunTimeException(exception);
            }
        }
    }

    public static List<Method> findMethod(Class type) {
        return reflectLoader.find(type, new MethodReflectHandler());
    }

    public static List<Field> findField(Class type) {
        return reflectLoader.find(type, new FieldReflectHandler());
    }

    public static <T> List<T> find(Class type, ReflectHandler<T> reflectHandler) {
        return reflectLoader.find(type, reflectHandler);
    }

    public static Class loadClass(String type) {
        return loadClass(type, Thread.currentThread().getContextClassLoader());
    }

    public static Class loadClass(String type, ClassLoader classLoader) {
        try {
            return classLoader.loadClass(type);
        } catch (ClassNotFoundException e) {
            throw new DreamRunTimeException(e);
        }
    }

    public static Class castClass(Class type) {
        switch (type.getName()) {
            case "byte":
                return Byte.class;
            case "int":
                return Integer.class;
            case "char":
                return Character.class;
            case "short":
                return Short.class;
            case "long":
                return Long.class;
            case "float":
                return Float.class;
            case "double":
                return Double.class;
            case "boolean":
                return Boolean.class;
            default:
                return type;
        }
    }

    public static boolean isBaseClass(Class type) {
        switch (castClass(type).getName()) {
            case "java.lang.Byte":
            case "java.lang.Integer":
            case "java.lang.Character":
            case "java.lang.Short":
            case "java.lang.Long":
            case "java.lang.Float":
            case "java.lang.Double":
            case "java.lang.String":
            case "java.lang.Boolean":
            case "java.lang.Object":
            case "java.util.Date":
            case "java.sql.Date":
                return true;
            default:
                return false;
        }
    }

    public static Class<? extends Collection> getRowType(Class typeClass, Method method) {
        Type genericType = reflectLoader.getGenericType(typeClass, method);
        return getRowType(genericType);
    }

    public static Class<? extends Collection> getRowType(Class typeClass, Field field) {
        Type genericType = reflectLoader.getGenericType(typeClass, field);
        return getRowType(genericType);
    }

    public static Class<? extends Collection> getRowType(Type type) {
        Type rawType;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            rawType = parameterizedType.getRawType();
        } else {
            rawType = type;
        }
        if (rawType instanceof Class) {
            return Collection.class.isAssignableFrom((Class) rawType) ? ((Class) rawType) : null;
        } else {
            return null;
        }
    }

    public static Class<?> getColType(Class typeClass, Method method) {
        Type genericType = reflectLoader.getGenericType(typeClass, method);
        return getColType(genericType);
    }

    public static Class<?> getColType(Class typeClass, Field field) {
        Type genericType = reflectLoader.getGenericType(typeClass, field);
        return getColType(genericType);
    }

    public static Class<?> getColType(Type type) {
        Type rawType;
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            rawType = parameterizedType.getRawType();
            if (rawType instanceof Class) {
                Class classType = (Class) rawType;
                if (Collection.class.isAssignableFrom(classType)) {
                    rawType = parameterizedType.getActualTypeArguments()[0];
                }
            }
        } else {
            rawType = type;
        }
        if (rawType instanceof Class) {
            return (Class<?>) rawType;
        } else {
            return Object.class;
        }
    }

    public static Map<String, Object> getAnnotationMap(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        Method[] methods = annotationType.getDeclaredMethods();
        Map<String, Object> paramMap = new HashMap<>(4);
        if (!ObjectUtil.isNull(methods)) {
            for (Method method : methods) {
                try {
                    Object value = method.invoke(annotation);
                    paramMap.put(method.getName(), value);
                } catch (Exception e) {
                    throw new DreamRunTimeException(e);
                }
            }
        }
        return paramMap;
    }
}
