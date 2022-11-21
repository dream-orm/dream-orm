package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;

import java.util.List;

public class BatchUpdateNonByIdMapper extends UpdateNonByIdMapper {
    private int batchSize;

    public BatchUpdateNonByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg) {
        return session.batchExecute(methodInfo, (List<?>) arg, batchSize);
    }

    public void execute(Class<?> type, List<?> viewList, int batchSize) {
        this.batchSize = batchSize;
        super.execute(type, viewList);
    }
}
