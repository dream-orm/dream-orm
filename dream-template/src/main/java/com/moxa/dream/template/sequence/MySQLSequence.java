package com.moxa.dream.template.sequence;

import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.ObjectWrapper;

import java.lang.reflect.Array;

public class MySQLSequence implements Sequence {
    private TableInfo tableInfo;

    @Override
    public void init(TableInfo tableInfo) {
        this.tableInfo = tableInfo;
    }

    @Override
    public boolean before() {
        return false;
    }

    @Override
    public String[] columnNames(TableInfo tableInfo) {
        return new String[]{tableInfo.getPrimColumnInfo().getName()};
    }


    @Override
    public void sequence(TableInfo tableInfo, ObjectWrapper wrapper, String property, Object arg) {
        if (arg != null && arg.getClass().isArray() && Array.getLength(arg) == 1) {
            wrapper.set(property, Array.get(arg, 0));
        }
    }
}
