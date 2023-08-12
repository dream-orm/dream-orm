package com.dream.template.sequence;

import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;

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
    protected Object sequence(ColumnInfo columnInfo) {
        return null;
    }
}
