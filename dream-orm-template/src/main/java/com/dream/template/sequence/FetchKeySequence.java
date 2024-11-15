package com.dream.template.sequence;

import com.dream.system.config.MappedParam;
import com.dream.system.config.MappedStatement;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.util.common.ObjectMap;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;

import java.lang.reflect.Array;
import java.util.List;
import java.util.stream.Collectors;

public class FetchKeySequence implements SequenceWrapper {
    private final Sequence sequence;

    public FetchKeySequence(Sequence sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isAutoIncrement(TableInfo tableInfo) {
        return sequence.isAutoIncrement(tableInfo);
    }

    @Override
    public String[] columnNames(TableInfo tableInfo) {
        if (isAutoIncrement(tableInfo)) {
            List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
            if (!ObjectUtil.isNull(primKeys)) {
                String[] columnNames = new String[primKeys.size()];
                for (int i = 0; i < primKeys.size(); i++) {
                    columnNames[i] = primKeys.get(i).getColumn();
                }
                return columnNames;
            }
        }
        return new String[0];
    }

    @Override
    public void sequence(TableInfo tableInfo, MappedStatement mappedStatement, Object result) {
        if (!isAutoIncrement(tableInfo)) {
            sequence.sequence(tableInfo, mappedStatement, result);
            ObjectMap argMap = (ObjectMap) mappedStatement.getArg();
            ObjectWrapper wrapper = argMap.wrapper();
            List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
            List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
            for (int i = 0; i < primKeys.size(); i++) {
                ColumnInfo columnInfo = primKeys.get(i);
                String name = columnInfo.getName();
                Object paramValue = mappedParamList.get(i).getParamValue();
                wrapper.set(name, paramValue);
            }
        } else {
            List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
            if (!ObjectUtil.isNull(primKeys)) {
                ObjectMap argMap = (ObjectMap) mappedStatement.getArg();
                ObjectWrapper wrapper = argMap.wrapper();
                List<String> columnList = primKeys.stream().map(ColumnInfo::getName).collect(Collectors.toList());
                for (int i = 0; i < columnList.size(); i++) {
                    wrapper.set(columnList.get(i), Array.get(result, i));
                }
            }
        }
    }
}
