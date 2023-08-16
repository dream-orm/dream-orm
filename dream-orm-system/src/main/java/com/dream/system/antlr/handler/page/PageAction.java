package com.dream.system.antlr.handler.page;


import com.dream.system.annotation.PageQuery;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.action.Action;
import com.dream.system.core.session.Session;
import com.dream.util.common.NonCollection;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;

import java.util.Map;

public class PageAction implements Action {
    private MethodInfo methodInfo;
    private String property;
    public PageAction(MethodInfo methodInfo, String sql) {
        PageQuery pageQuery = methodInfo.get(PageQuery.class);
        String value = pageQuery.value();
        String property = "total";
        if (!ObjectUtil.isNull(value)) {
            property = value + "." + property;
        }
        this.property=property;
        this.methodInfo = new MethodInfo()
                .setId(methodInfo.getId() + "#count")
                .setConfiguration(methodInfo.getConfiguration())
                .setRowType(NonCollection.class)
                .setColType(Long.class)
                .setSql(sql);
    }

    @Override
    public void doAction(Session session, MappedStatement mappedStatement, Object arg) {
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
