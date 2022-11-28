package com.moxa.dream.template.sequence;

import com.moxa.dream.system.table.TableInfo;

public class MySQLSequence extends AbstractSequence {
    @Override
    public boolean before() {
        return false;
    }

    @Override
    public String[] columnNames(TableInfo tableInfo) {
        return new String[0];
    }

    @Override
    protected Object sequence(TableInfo tableInfo) {
        return null;
    }
}
