package com.dream.helloworld.h2;

import com.dream.system.action.ActionProcessor;
import com.dream.system.config.Configuration;
import com.dream.system.config.MappedStatement;
import com.dream.system.core.session.Session;
import com.dream.util.common.ObjectWrapper;

import java.lang.reflect.Field;
import java.util.Map;

public class MaskProcessor implements ActionProcessor {
    private String fieldName;
    private Map<String, Object> paramMap;

    @Override
    public void init(Field field, Map<String, Object> paramMap, Configuration configuration) {
        this.fieldName = field.getName();
        this.paramMap = paramMap;
    }

    @Override
    public void loop(Object row, MappedStatement mappedStatement, Session session) {
        ObjectWrapper.wrapper(row).set(fieldName, "hello world");
    }
}
