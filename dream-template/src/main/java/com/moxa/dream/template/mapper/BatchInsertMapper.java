package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.template.resolve.MappedResolve;
import com.moxa.dream.template.sequence.Sequence;

import java.util.List;

public class BatchInsertMapper extends InsertMapper {

    public BatchInsertMapper(Session session, Sequence sequence) {
        super(session, sequence);
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg, MappedResolve mappedResolve) {
        BatchMappedStatement batchMappedStatement = new BatchMappedStatement(methodInfo, (List<?>) arg);
        return super.execute(batchMappedStatement, mappedResolve);
    }
}
