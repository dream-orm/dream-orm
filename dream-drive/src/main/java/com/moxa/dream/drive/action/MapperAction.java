package com.moxa.dream.drive.action;

import com.moxa.dream.drive.session.DefaultSession;
import com.moxa.dream.drive.session.Session;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapper.action.Action;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class MapperAction implements Action {
    private Class type;
    private Method method;
    private String property;
    private Configuration configuration;

    public MapperAction(Configuration configuration, String property, String classMethodName) {
        int index = classMethodName.lastIndexOf(".");
        if (index == -1) {
            throw new DreamRunTimeException("方法全类名'" + classMethodName + "'不能解析成类名+方法名");
        }
        Class type = ReflectUtil.loadClass(classMethodName.substring(0, index));
        String methodName = classMethodName.substring(index + 1);
        List<Method> methodList = ReflectUtil.findMethod(type)
                .stream()
                .filter(method -> method.getName().equals(methodName))
                .collect(Collectors.toList());
        if(ObjectUtil.isNull(methodList)){
            throw new DreamRunTimeException("方法'" + methodName + "'未在类'" + type.getName() + "'注册");
        }
        if (methodList.size() > 1) {
            throw new DreamRunTimeException("方法'" + methodName + "'在类'" + type.getName() + "'存在多个");
        }
        Method method = methodList.get(0);
        this.configuration = configuration;
        this.type = type;
        this.method = method;
        this.property = property;
    }

    @Override
    public Object doAction(Executor executor, Object arg) throws Exception {
        Session session = new DefaultSession(configuration, executor);
        Object mapper = session.getMapper(type);
        Object result = method.invoke(mapper, arg);
        if (!ObjectUtil.isNull(property)) {
            ObjectWrapper.wrapper(arg).set(property, result);
        }
        return null;
    }
}
