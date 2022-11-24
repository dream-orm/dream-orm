package com.moxa.dream.system.core.action;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceAction implements Action {
    private final String property;
    private final Object target;
    private final Method method;

    public ServiceAction(String property, String type) {
        int index = type.lastIndexOf(".");
        if (index == -1) {
            throw new DreamRunTimeException(type + "不是一个类方法");
        }
        String className = type.substring(0, index);
        String methodName = type.substring(index + 1);
        Class<?> typeClass = ReflectUtil.loadClass(className);
        List<Method> methodList = ReflectUtil.findMethod(typeClass).stream().filter(method -> method.getName().equals(methodName) && method.getParameters().length == 1).collect(Collectors.toList());
        if (methodList == null) {
            throw new DreamRunTimeException("方法" + type + "且参数个数等于1不存在");
        } else if (methodList.size() > 1) {
            throw new DreamRunTimeException("方法" + type + "且参数个数等于1存在" + methodList.size() + "个");
        }
        this.property = property;
        this.target = ReflectUtil.create(typeClass);
        this.method = methodList.get(0);
    }

    @Override
    public void doAction(Session session, Object arg) throws Exception {
        Object result = method.invoke(target, arg);
        if (!ObjectUtil.isNull(property)) {
            ObjectWrapper.wrapper(arg).set(property, result);
        }
    }

}
