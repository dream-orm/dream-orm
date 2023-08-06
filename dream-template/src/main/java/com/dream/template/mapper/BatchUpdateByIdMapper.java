package com.dream.template.mapper;

import com.dream.system.config.BatchMappedStatement;
import com.dream.system.config.Command;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;

import java.util.List;

public class BatchUpdateByIdMapper extends UpdateByIdMapper {

    public BatchUpdateByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected Object executeValidate(MethodInfo methodInfo, Object arg) {
        return super.execute(new BatchMappedStatement(methodInfo, (List<?>) arg));
    }

    @Override
    protected Command getCommand() {
        return Command.BATCH;
    }
}
