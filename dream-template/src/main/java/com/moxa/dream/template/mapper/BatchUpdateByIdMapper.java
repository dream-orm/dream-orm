package com.moxa.dream.template.mapper;

import com.moxa.dream.system.config.MethodInfo;
import com.moxa.dream.system.core.session.Session;

import java.util.List;

public class BatchUpdateByIdMapper extends UpdateByIdMapper {
    ThreadLocal<Integer> threadLocal = new ThreadLocal();

    public BatchUpdateByIdMapper(Session session) {
        super(session);
    }

    @Override
    protected Object execute(MethodInfo methodInfo, Object arg) {
        Integer batchSize = threadLocal.get();
        return session.batchExecute(methodInfo, (List<?>) arg, batchSize);
    }

    public void execute(Class<?> type, List<?> viewList, int batchSize) {
        threadLocal.set(batchSize);
        try {
            super.execute(type, viewList);
        }finally {
            threadLocal.remove();
        }
    }
}
