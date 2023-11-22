package com.dream.template.sequence;

import com.dream.system.config.MappedStatement;
import com.dream.system.table.TableInfo;

public class NoFetchKeySequence implements Sequence {
    private Sequence sequence;

    public NoFetchKeySequence(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isAutoIncrement(TableInfo tableInfo) {
        return sequence.isAutoIncrement(tableInfo);
    }

    @Override
    public String[] columnNames(TableInfo tableInfo) {
        return new String[0];
    }

    @Override
    public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object result) {
        if (!isAutoIncrement(tableInfo)) {
            sequence.sequence(tableInfo, mappedStatement, result);
        }
    }
}
