package com.moxa.dream.driver.action;

import com.moxa.dream.antlr.config.Command;
import com.moxa.dream.driver.session.DefaultSqlSession;
import com.moxa.dream.driver.session.SqlSession;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.core.executor.Executor;
import com.moxa.dream.system.mapped.MappedStatement;
import com.moxa.dream.system.mapper.Action;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
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
        ObjectUtil.requireTrue(index > 0, classMethodName + " not class method name");
        Class type = ReflectUtil.loadClass(classMethodName.substring(0, index));
        String methodName = classMethodName.substring(index + 1);
        List<Method> methodList = ReflectUtil.findMethod(type)
                .stream()
                .filter(method -> method.getName().equals(methodName))
                .collect(Collectors.toList());
        ObjectUtil.requireNonNull(methodList, methodName + " not exist");
        ObjectUtil.requireTrue(methodList.size() == 1, methodName + " must exist one");
        Method method = methodList.get(0);
        this.configuration = configuration;
        this.type = type;
        this.method = method;
        this.property = property;
    }

    @Override
    public void doAction(Executor executor, Object arg) throws Exception {
        SqlSession sqlSession = new DefaultSqlSession(configuration, executor);
        Object mapper = sqlSession.getMapper(type);
        Object result = method.invoke(mapper, arg);
        if (!ObjectUtil.isNull(property)) {
            if (arg == null) {
                throw new RuntimeException("Property 'arg' is required");
            }
            ObjectWrapper.wrapper(arg).set(property, result);
        }
    }
}
