package com.moxa.dream.template.mapper.fetch;

import com.moxa.dream.system.config.Configuration;

public class MSSQLFetchKey extends SequenceFetchKey {

    public MSSQLFetchKey(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected String getSequenceSql(String sequenceColumn) {
        return "select next value for " + sequenceColumn;
    }
}
