package com.moxa.dream.template.mapper;

import com.moxa.dream.system.annotation.Batch;
import com.moxa.dream.system.config.Configuration;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.system.table.TableInfo;

import java.lang.annotation.Annotation;

public class InsertBatchTemplateMapper extends InsertTemplateMapper {
    public InsertBatchTemplateMapper(Session session) {
        super(session);
    }

    @Override
    protected MethodInfo doGetMethodInfo(Configuration configuration, TableInfo tableInfo, Class type) {
        MethodInfo methodInfo = super.doGetMethodInfo(configuration, tableInfo, type);
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
}
