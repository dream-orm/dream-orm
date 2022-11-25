package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.BatchMappedStatement;
import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;

import java.util.List;

public class BatchInsertMapper extends InsertMapper {
    ThreadLocal<Integer> threadLocal = new ThreadLocal();

    public BatchInsertMapper(Session session) {
        super(session);
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg) {
        Integer batchSize = threadLocal.get();
        return session.execute(new BatchMappedStatement(methodInfo, (List<?>) arg, batchSize));
    }

    public Object execute(Class<?> type, List<?> viewList, int batchSize) {
        threadLocal.set(batchSize);
        try {
            return super.execute(type, viewList);
        } finally {
            threadLocal.remove();
        }
    }
}
