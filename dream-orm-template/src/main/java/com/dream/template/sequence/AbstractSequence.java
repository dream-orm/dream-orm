package com.dream.template.sequence;

import com.dream.system.config.MappedParam;
import com.dream.system.config.MappedStatement;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;

import java.util.List;

public abstract class AbstractSequence implements Sequence {
    @Override
    public boolean isAutoIncrement(TableInfo tableInfo) {
        return false;
    }

    @Override
    public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object result) {
        List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
        List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
        for (int i = 0; i < primKeys.size(); i++) {
            MappedParam mappedParam = mappedParamList.get(i);
            Object paramValue = mappedParam.getParamValue();
            if (paramValue == null) {
                ColumnInfo columnInfo = primKeys.get(i);
                Object sequence = sequence(columnInfo);
                if (sequence != null) {
                    mappedParam.setParamValue(sequence);
                }
            }
        }
    }

    protected abstract Object sequence(ColumnInfo columnInfo);

}
