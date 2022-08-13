package com.moxa.dream.driver.action;

import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapper.Action;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceAction implements Action {
    private Object target;
    private Method method;
    private String property;

    public ServiceAction(String property, String classMethodName) {
        int index = classMethodName.lastIndexOf(".");
        ObjectUtil.requireTrue(index > 0, classMethodName + " not class method name");
        Class type = ReflectUtil.loadClass(classMethodName.substring(0, index));
        target = ReflectUtil.create(type);
        String methodName = classMethodName.substring(index + 1);
        List<Method> methodList = ReflectUtil.findMethod(type)
                .stream()
                .filter(method -> method.getName().equals(methodName))
                .collect(Collectors.toList());
        ObjectUtil.requireNonNull(methodList, methodName + " not exist");
        ObjectUtil.requireTrue(methodList.size() == 1, methodName + " must exist one");
        Method method = methodList.get(0);
        this.property = property;
        this.method = method;
    }

    @Override
    public void doAction(Executor executor, Object arg) throws Exception {
        Object result = method.invoke(target, arg);
        if (!ObjectUtil.isNull(property)) {
            if (arg == null) {
                throw new RuntimeException("Property 'arg' is required");
            }
            ObjectWrapper.wrapper(arg).set(property, result);
        }
    }
}
