package com.moxa.dream.template.mapper.fetch;

import com.moxa.dream.system.config.Configuration;

public class PGSQLFetchKey extends SequenceFetchKey {

    public PGSQLFetchKey(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected String getSequenceSql(String sequenceColumn) {
        return "select nextval(" + sequenceColumn + ")";
    }
}
