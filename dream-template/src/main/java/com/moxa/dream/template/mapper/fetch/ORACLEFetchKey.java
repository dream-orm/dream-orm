package com.moxa.dream.template.mapper.fetch;

import com.moxa.dream.system.config.Configuration;

public class ORACLEFetchKey extends SequenceFetchKey {

    public ORACLEFetchKey(Configuration configuration) {
        super(configuration);
    }

    @Override
    protected String getSequenceSql(String sequenceColumn) {
        return "select " + sequenceColumn + ".nextval from dual";
    }
}
