package com.dream.template.sequence;

import com.dream.system.config.MappedParam;
import com.dream.system.config.MappedStatement;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.template.mapper.AbstractMapper;
import com.dream.util.common.ObjectUtil;
import com.dream.util.common.ObjectWrapper;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FetchKeySequence implements SequenceWrapper {
    private Sequence sequence;

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
            Map<String, Object> argMap = (Map<String, Object>) mappedStatement.getArg();
            ObjectWrapper wrapper = ObjectWrapper.wrapper(argMap.get(AbstractMapper.DREAM_TEMPLATE_PARAM));
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
                Map<String, Object> argMap = (Map<String, Object>) mappedStatement.getArg();
                ObjectWrapper wrapper = ObjectWrapper.wrapper(argMap.get(AbstractMapper.DREAM_TEMPLATE_PARAM));
                List<String> columnList = primKeys.stream().map(ColumnInfo::getName).collect(Collectors.toList());
                for (int i = 0; i < columnList.size(); i++) {
                    wrapper.set(columnList.get(i), Array.get(result, 0));
                }
            }
        }
    }
}
