package com.dream.drive.listener;

import com.dream.flex.def.ConditionDef;
import com.dream.flex.def.FromDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.mapper.FlexMapper;
import com.dream.system.config.Command;
import com.dream.system.config.Configuration;
import com.dream.system.config.MappedParam;
import com.dream.system.config.MappedStatement;
import com.dream.system.core.listener.Listener;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.table.factory.TableFactory;
import com.dream.util.common.LowHashMap;
import com.dream.util.common.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractAuditListener implements Listener {
    @Override
    public void before(MappedStatement mappedStatement) {
        if (intercept(mappedStatement)) {
            Command command = mappedStatement.getCommand();
            if (Command.UPDATE.equals(command) || Command.INSERT.equals(command) || Command.DELETE.equals(command)) {
                Set<String> tableSet = mappedStatement.getTableSet();
                String[] tables = tableSet.toArray(new String[0]);
                Configuration configuration = mappedStatement.getConfiguration();
                TableFactory tableFactory = configuration.getTableFactory();
                TableInfo tableInfo = tableFactory.getTableInfo(tables[0]);
                if (tableInfo != null) {
                    List<ColumnInfo> primKeys = tableInfo.getPrimKeys();
                    if (!ObjectUtil.isNull(primKeys)) {
                        Map<String, Object> primValueMap = new LowHashMap<>();
                        Map<String, Object> columnValueMap = new LowHashMap<>();
                        List<MappedParam> mappedParamList = mappedStatement.getMappedParamList();
                        if (!ObjectUtil.isNull(mappedParamList)) {
                            for (MappedParam mappedParam : mappedParamList) {
                                String paramName = mappedParam.getParamName();
                                int index = paramName.lastIndexOf(".");
                                String fieldName = paramName.substring(index + 1);
                                ColumnInfo columnInfo = tableInfo.getColumnInfo(fieldName);
                                if (columnInfo == null) {
                                    return;
                                }
                                if (intercept(tableInfo, columnInfo)) {
                                    String column = columnInfo.getColumn();
                                    columnValueMap.put(column, mappedParam.getParamValue());
                                }
                            }
                        }
                        for (ColumnInfo columnInfo : primKeys) {
                            String column = columnInfo.getColumn();
                            Object value = columnValueMap.remove(column);
                            if (value == null) {
                                return;
                            }
                            primValueMap.put(column, value);
                        }
                        List<AuditColumn> auditColumnList = new ArrayList<>(columnValueMap.size());
                        if (Command.INSERT.equals(command)) {
                            for (Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
                                Object value = entry.getValue();
                                if (value != null) {
                                    auditColumnList.add(new AuditColumn(entry.getKey(), null, value));
                                }
                            }
                        } else if (Command.UPDATE.equals(command)) {
                            Map<String, Object> valueMap = query(mappedStatement, tableInfo, columnValueMap.keySet(), primValueMap);
                            for (Map.Entry<String, Object> entry : columnValueMap.entrySet()) {
                                String key = entry.getKey();
                                Object value = entry.getValue();
                                if (!ObjectUtil.isNull(valueMap)) {
                                    Object oldValue = valueMap.get(key);
                                    if (value != null || oldValue != null) {
                                        if (value == null || !value.equals(oldValue)) {
                                            auditColumnList.add(new AuditColumn(key, oldValue, value));
                                        }
                                    }
                                } else {
                                    auditColumnList.add(new AuditColumn(key, null, value));
                                }
                            }
                        } else {
                            Map<String, Object> valueMap = query(mappedStatement, tableInfo, null, primValueMap);
                            if (valueMap != null) {
                                for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
                                    Object value = entry.getValue();
                                    if (value != null) {
                                        auditColumnList.add(new AuditColumn(entry.getKey(), value, null));
                                    }
                                }
                            }
                        }
                        mappedStatement.set(AuditColumnList.class, new AuditColumnList(tableInfo, primValueMap, auditColumnList));
                    }
                }
            }
        }
    }

    @Override
    public void afterReturn(Object result, MappedStatement mappedStatement) {
        AuditColumnList auditColumnList = mappedStatement.get(AuditColumnList.class);
        if (auditColumnList != null) {
            handle(mappedStatement.getCommand(), auditColumnList.getTableInfo(), auditColumnList.getPrimValueMap(), auditColumnList.getAuditColumnList());
        }
    }

    @Override
    public void exception(Throwable e, MappedStatement mappedStatement) {

    }

    protected boolean intercept(MappedStatement mappedStatement) {
        return true;
    }

    protected boolean intercept(TableInfo tableInfo, ColumnInfo columnInfo) {
        return true;
    }

    protected Map<String, Object> query(MappedStatement mappedStatement, TableInfo tableInfo, Set<String> columnSet, Map<String, Object> primValueMap) {
        FlexMapper flexMapper = flexMapper(mappedStatement);
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

    protected abstract FlexMapper flexMapper(MappedStatement mappedStatement);

    protected abstract void handle(Command command, TableInfo tableInfo, Map<String, Object> primValueMap, List<AuditColumn> auditColumnList);

    static class AuditColumnList {
        private final TableInfo tableInfo;
        private final Map<String, Object> primValueMap;
        private final List<AuditColumn> auditColumnList;

        public AuditColumnList(TableInfo tableInfo, Map<String, Object> primValueMap, List<AuditColumn> auditColumnList) {
            this.tableInfo = tableInfo;
            this.primValueMap = primValueMap;
            this.auditColumnList = auditColumnList;
        }

        public TableInfo getTableInfo() {
            return tableInfo;
        }

        public Map<String, Object> getPrimValueMap() {
            return primValueMap;
        }

        public List<AuditColumn> getAuditColumnList() {
            return auditColumnList;
        }
    }

    public static class AuditColumn {
        private final String column;
        private final Object oldValue;
        private final Object newValue;

        public AuditColumn(String column, Object oldValue, Object newValue) {
            this.column = column;
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        public String getColumn() {
            return column;
        }

        public Object getOldValue() {
            return oldValue;
        }

        public Object getNewValue() {
            return newValue;
        }
    }
}
