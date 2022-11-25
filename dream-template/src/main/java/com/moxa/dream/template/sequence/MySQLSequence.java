package com.moxa.dream.template.sequence;

import com.moxa.dream.system.table.TableInfo;

import java.lang.reflect.Array;

public class MySQLSequence extends AbstractSequence {
    @Override
    public boolean before() {
        return false;
    }

    @Override
    public String[] columnNames(TableInfo tableInfo) {
        return new String[]{tableInfo.getPrimColumnInfo().getName()};
    }

    @Override
    protected Object sequence(TableInfo tableInfo, Object arg) {
        return Array.get(arg, 0);
    }
}
