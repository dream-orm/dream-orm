package com.dream.system.core.action;


import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectUtil;
import com.dream.util.exception.DreamRunTimeException;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

public class SqlAction implements Action {
    private MethodInfo methodInfo;
    private BiConsumer<Object, Object> consumer;

    public SqlAction(MethodInfo methodInfo, String sql, Class<? extends Collection> rowType, Class<?> colType, BiConsumer<Object, Object> consumer) {
        if (methodInfo == null) {
            throw new DreamRunTimeException("methodInfo不能为空");
        }
        if (ObjectUtil.isNull(sql)) {
            throw new DreamRunTimeException("sql不能为空");
        }
        if (rowType == null) {
            throw new DreamRunTimeException("rowType不能为空");
        }
        if (colType == null) {
            throw new DreamRunTimeException("colType不能为空");
        }
        if (consumer == null) {
            throw new DreamRunTimeException("consumer不能为空");
        }
        this.methodInfo = new MethodInfo()
                .setId(methodInfo.getId() + "#count")
                .setConfiguration(methodInfo.getConfiguration())
                .setRowType(rowType)
                .setColType(colType)
                .setSql(sql);
        this.consumer = consumer;
    }

    @Override
    public void doAction(Session session, MappedStatement mappedStatement, Object arg) {
        Map<String, Object> argMap;
        if (arg instanceof Map) {
            argMap = (Map<String, Object>) arg;
        } else {
            argMap = new ObjectMap(arg);
        }
        Object result = session.execute(methodInfo, argMap);
        consumer.accept(arg, result);
    }
}
