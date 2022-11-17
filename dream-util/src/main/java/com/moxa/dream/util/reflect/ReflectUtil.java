package com.moxa.dream.util.reflect;

import com.moxa.dream.util.common.ObjectUtil;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.List;

public class ReflectUtil {
    private static final ReflectLoader reflectLoader = new ReflectLoader();

    public static <T> T create(Class<T> type) {
        try {
            return type.getConstructor().newInstance();
        } catch (Exception e) {
            try {
                Constructor<T> constructor = type.getDeclaredConstructor();
                constructor.setAccessible(true);
                return constructor.newInstance();
            } catch (Exception exception) {
                throw new ReflectException(exception);
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
            throw new ReflectException(e);
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
        } else
            return null;
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
        } else
            return Object.class;
    }

    public static <T> void copy(T target, T source) {
        List<Field> fieldList = ReflectUtil.findField(source.getClass());
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(source);
                    field.set(target, value);
                } catch (Exception e) {
                    throw new ReflectException(e);
                }
            }
        }
    }
}
