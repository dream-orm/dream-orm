package com.moxa.dream.system.core.action;


import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.NonCollection;
import com.moxa.dream.util.common.ObjectMap;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.common.ObjectWrapper;
import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.Collection;
import java.util.Map;

public class SqlAction implements Action {
    private String property;

    private MethodInfo methodInfo;

    public SqlAction(Configuration configuration, String sql) {
        this(configuration, null, sql);
    }

    public SqlAction(Configuration configuration, String property, String sql) {
        this(configuration, property, sql, NonCollection.class, Object.class);
    }

    public SqlAction(Configuration configuration, String property, String sql, Class<? extends Collection> rowType, Class<?> colType) {
        this(configuration, property, sql, rowType, colType, true);
    }

    public SqlAction(Configuration configuration, String property, String sql, Class<? extends Collection> rowType, Class<?> colType, boolean cache) {
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
        this.property = property;
        methodInfo = new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(rowType)
                .setColType(colType)
                .setSql(sql)
                .setCache(cache);
    }

    @Override
    public void doAction(Session session, MappedStatement mappedStatement, Object arg) throws Exception {
        Map<String, Object> argMap;
        if (arg instanceof Map) {
            argMap = (Map<String, Object>) arg;
        } else {
            argMap = new ObjectMap(arg);
        }
        ObjectWrapper wrapper = ObjectWrapper.wrapper(arg);
        Object result = session.execute(methodInfo, argMap);
        if (!ObjectUtil.isNull(property)) {
            wrapper.set(property, result);
        }
    }
}
