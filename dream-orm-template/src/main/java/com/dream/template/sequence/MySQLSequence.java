package com.dream.template.sequence;

import com.dream.system.config.MappedStatement;
import com.dream.system.table.TableInfo;

public class MySQLSequence implements Sequence {
    @Override
    public boolean isAutoIncrement(TableInfo tableInfo) {
        return true;
    }

    @Override
    public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object result) {

    }
}
