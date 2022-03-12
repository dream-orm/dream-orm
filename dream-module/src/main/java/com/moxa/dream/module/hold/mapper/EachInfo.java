package com.moxa.dream.module.hold.mapper;

import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

public class EachInfo {
    private Class type;
    private Method method;
    private Map<String, String> argMap;
    private String field;
    private Object[] args;

    public EachInfo(Class type, Method method, String field, Map<String, String> argMap) {
        this.type = type;
        this.method = method;
        this.field = field;
        this.argMap = argMap;
        initArgs(method);
    }

    public Class getType() {
        return type;
    }

    public Method getMethod() {
        return method;
    }

    public Map<String, String> getArgMap() {
        return argMap;
    }

    public String getField() {
        return field;
    }

    public Object[] getArgs() {
        return args;
    }

    private void initArgs(Method method) {
        Parameter[] parameters = method.getParameters();
        args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (Number.class.isAssignableFrom(ReflectUtil.castClass(parameter.getType()))) {
                args[i] = 0;
            } else {
                args[i] = null;
            }
        }
    }
}