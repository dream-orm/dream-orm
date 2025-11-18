package com.dream.system.action;

import com.dream.system.config.Configuration;
import com.dream.system.config.MappedParam;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectWrapper;
import com.dream.util.exception.DreamRunTimeException;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class FetchActionProcessor implements ActionProcessor {
    private String fieldName;
    private MethodInfo methodInfo;
    private boolean fast;

    @Override
    public void init(Field field, Map<String, Object> paramMap, Configuration configuration) {
        this.fieldName = field.getName();
        String sql = (String) paramMap.get("value");
        this.fast = (boolean) paramMap.get("fast");
        this.methodInfo = new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(ReflectUtil.getRowType(field.getGenericType()))
                .setColType(ReflectUtil.getColType(field.getGenericType()))
                .setSql(sql);
    }

    @Override
    public void loop(Object row, MappedStatement mappedStatement, Session session) {
        if (row != null) {
            Object result;
            ObjectMap objectRow = new ObjectMap(row);
            if (fast) {
                String key = "fetch:" + fieldName;
                MappedStatement statement = (MappedStatement) mappedStatement.get(key);
                if (statement == null) {
                    try {
                        statement = session.getConfiguration().getDialectFactory().compile(methodInfo, objectRow);
                        mappedStatement.put(key, statement);
                    } catch (Exception e) {
                        throw new DreamRunTimeException(e);
                    }
                } else {
                    List<MappedParam> mappedParamList = statement.getMappedParamList();
                    if (mappedParamList != null && !mappedParamList.isEmpty()) {
                        ObjectWrapper objectWrapper = ObjectWrapper.wrapper(objectRow);
                        for (MappedParam mappedParam : mappedParamList) {
                            mappedParam.setParamValue(objectWrapper.get(mappedParam.getParamName()));
                        }
                    }
                }
                result = session.execute(statement);
            } else {
                result = session.execute(methodInfo, objectRow);
            }
            if (result != null) {
                ObjectWrapper wrapper = ObjectWrapper.wrapper(row);
                wrapper.set(fieldName, result);
            }
        }
    }
}
