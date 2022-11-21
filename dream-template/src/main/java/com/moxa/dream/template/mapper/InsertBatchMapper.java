package com.moxa.dream.template.mapper;

import com.moxa.dream.system.annotation.Batch;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.ObjectMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertBatchMapper extends InsertMapper {
    public InsertBatchMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo doGetMethodInfo(Configuration configuration, TableInfo tableInfo, List<Field> fieldList, Object arg) {
        MethodInfo methodInfo = super.doGetMethodInfo(configuration, tableInfo, fieldList, arg);
        methodInfo.set(Batch.class, new Batch() {

            @Override
            public Class<? extends Annotation> annotationType() {
                return Batch.class;
            }

            @Override
            public int value() {
                return 1000;
            }
        });
        return methodInfo;
    }

    @Override
    protected Map<String, Object> wrapArg(Object arg) {
        if (arg != null) {
            if (arg instanceof Map) {
                return (Map<String, Object>) arg;
            } else {
                return new ObjectMap(arg);
            }
        } else {
            return new HashMap<>();
        }
    }
}
