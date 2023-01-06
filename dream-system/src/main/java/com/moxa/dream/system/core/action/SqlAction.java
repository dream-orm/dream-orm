package com.moxa.dream.system.core.action;


import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.ObjectMap;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

public class SqlAction implements Action {
    private MethodInfo methodInfo;
    private BiConsumer<Object, Object> consumer;

    public SqlAction(Configuration configuration, String sql, Class<? extends Collection> rowType, Class<?> colType, BiConsumer<Object, Object> consumer) {
        if (configuration == null) {
            throw new DreamRunTimeException("configuration不能为空");
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
        methodInfo = new MethodInfo()
                .setConfiguration(configuration)
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
