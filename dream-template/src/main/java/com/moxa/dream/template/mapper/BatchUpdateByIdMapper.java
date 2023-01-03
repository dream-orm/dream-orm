package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;

import java.util.List;

public class BatchUpdateByIdMapper extends UpdateByIdMapper {

    public BatchUpdateByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg) {
        return session.execute(new BatchMappedStatement(methodInfo, (List<?>) arg));
    }
}
