package com.dream.template.mapper;

import com.dream.system.config.AntlrBatchMappedStatement;
import com.dream.system.config.Command;
import com.dream.system.config.MethodInfo;
import com.dream.system.core.session.Session;
import com.dream.template.sequence.SequenceWrapper;

import java.util.List;

public class BatchInsertMapper extends InsertMapper {
    public BatchInsertMapper(Session session, SequenceWrapper sequence) {
        super(session, sequence);
    }

    @Override
    protected Object executeValidate(MethodInfo methodInfo, Object arg) {
        return super.execute(new AntlrBatchMappedStatement(methodInfo, (List<?>) arg));
    }

    @Override
    protected Command getCommand() {
        return Command.BATCH;
    }
}
