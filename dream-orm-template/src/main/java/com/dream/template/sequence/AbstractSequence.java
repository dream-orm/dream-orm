package com.dream.template.sequence;

import com.dream.system.config.MappedStatement;
import com.dream.system.table.TableInfo;

public abstract class AbstractSequence implements Sequence {
    @Override
    public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object arg) {
        Object paramValue = mappedStatement.getMappedParamList().get(0).getParamValue();
        if (paramValue == null) {
            Object sequence = sequence(tableInfo);
            if (sequence != null) {
                mappedStatement.getMappedParamList().get(0).setParamValue(sequence);
            }
        }
    }

    protected abstract Object sequence(TableInfo tableInfo);

}
