package com.dream.drive.listener;

import com.dream.flex.def.ConditionDef;
import com.dream.flex.def.FromDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.table.TableInfo;
import com.dream.util.common.LowHashMap;

import java.util.Map;
import java.util.Set;

public abstract class FlexAuditListener extends AbstractAuditListener {
    @Override
    protected Map<String, Object> query(TableInfo tableInfo, Set<String> columnSet, Map<String, Object> primValueMap) {
        FlexMapper flexMapper = flexMapper();
        ConditionDef conditionDef = null;
        for (Map.Entry<String, Object> entry : primValueMap.entrySet()) {
            ConditionDef _conditionDef = FunctionDef.column(entry.getKey()).eq(entry.getValue());
            if (conditionDef == null) {
                conditionDef = _conditionDef;
            } else {
                conditionDef = conditionDef.and(_conditionDef);
            }
        }
        FromDef fromDef;
        if (columnSet != null) {
            fromDef = FunctionDef.select(columnSet.toArray(new String[0]));
        } else {
            fromDef = FunctionDef.select();
        }
        return flexMapper.selectOne(fromDef.from(tableInfo.getTable()).where(conditionDef), LowHashMap.class);
    }

    protected abstract FlexMapper flexMapper();
}
