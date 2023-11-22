package com.dream.system.antlr.handler.page;


import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.action.InitAction;
import com.dream.system.core.session.Session;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectWrapper;

import java.util.Map;

public class PageAction implements InitAction {
    private MethodInfo methodInfo;
    private String property;

    public PageAction(MethodInfo methodInfo, String property) {
        this.methodInfo = methodInfo;
        this.property = property;
    }

    @Override
    public void init(MappedStatement mappedStatement, Session session) {
        Object arg = mappedStatement.getArg();
        Map<String, Object> argMap;
        if (arg instanceof Map) {
            argMap = (Map<String, Object>) arg;
        } else {
            argMap = new ObjectMap(arg);
        }
        ObjectWrapper wrapper = ObjectWrapper.wrapper(arg);
        long total = (long) wrapper.get(property);
        if (total == 0) {
            Object result = session.execute(methodInfo, argMap);
            wrapper.set(property, result);
        }
    }
}
