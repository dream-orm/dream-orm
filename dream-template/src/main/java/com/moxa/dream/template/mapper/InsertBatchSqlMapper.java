package com.moxa.dream.template.mapper;

import com.moxa.dream.system.core.session.Session;

public class InsertBatchSqlMapper extends InsertSqlMapper {
    public InsertBatchSqlMapper(Session session) {
        super(session);
    }

    @Override
    protected boolean isBatch() {
        return true;
    }
}
