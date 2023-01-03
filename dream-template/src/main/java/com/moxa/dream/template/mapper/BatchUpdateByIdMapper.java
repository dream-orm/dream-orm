package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.template.resolve.MappedResolve;

import java.util.List;

public class BatchUpdateByIdMapper extends UpdateByIdMapper {

    public BatchUpdateByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg, MappedResolve mappedResolve) {
        BatchMappedStatement batchMappedStatement = new BatchMappedStatement(methodInfo, (List<?>) arg);
        return super.execute(batchMappedStatement, mappedResolve);
    }
}
