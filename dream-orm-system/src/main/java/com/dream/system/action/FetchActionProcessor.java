package com.dream.system.action;

import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectWrapper;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Map;

public class FetchActionProcessor implements ActionProcessor {
    private String fieldName;
    private MethodInfo methodInfo;

    @Override
    public void init(Field field, Map<String, Object> paramMap, Configuration configuration) {
        this.fieldName = field.getName();
        String sql = (String) paramMap.get("value");
        this.methodInfo = new MethodInfo()
                .setConfiguration(configuration)
                .setRowType(ReflectUtil.getRowType(field.getGenericType()))
                .setColType(ReflectUtil.getColType(field.getGenericType()))
                .setSql(sql);
    }

    @Override
    public void loop(Object row, MappedStatement mappedStatement, Session session) {
        if (row != null) {
            Object result = session.execute(methodInfo, new ObjectMap(row));
            if (result != null) {
                ObjectWrapper wrapper = ObjectWrapper.wrapper(row);
                wrapper.set(fieldName, result);
            }
        }
    }
}
