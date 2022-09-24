package com.moxa.dream.template.mapper;

import com.moxa.dream.system.core.session.Session;
import com.moxa.dream.util.common.ObjectMap;

import java.util.Map;

public class InsertBatchSqlMapper extends InsertSqlMapper {
    public InsertBatchSqlMapper(Session session) {
        super(session);
    }

    @Override
    protected boolean isBatch() {
        return true;
    }

    @Override
    protected Map<String, Object> wrapArg(Object arg) {
        return new ObjectMap(arg);
    }
}
