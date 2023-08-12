package com.dream.system.table;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TableInfo {
    private final String table;
    private final Map<String, ColumnInfo> columnInfoMap;
    private final Map<String, JoinInfo> joinInfoMap;
    private final Map<String, String> fieldMap;
    private final List<ColumnInfo> primKeys;

    public TableInfo(String table, List<ColumnInfo> primKeys, Map<String, ColumnInfo> columnInfoMap, Map<String, JoinInfo> joinInfoMap, Map<String, String> fieldMap) {
        this.table = table;
        this.primKeys = primKeys;
        this.columnInfoMap = columnInfoMap;
        this.joinInfoMap = joinInfoMap;
        this.fieldMap = fieldMap;
    }

    public String getFieldName(String label) {
        return fieldMap.get(label);
    }

    public ColumnInfo getColumnInfo(String fieldName) {
        return columnInfoMap.get(fieldName);
    }

    public Collection<ColumnInfo> getColumnInfoList() {
        return columnInfoMap.values();
    }

    public String getTable() {
        return table;
    }

    public List<ColumnInfo> getPrimKeys() {
        return primKeys;
    }

    public JoinInfo getJoinInfo(String joinTable) {
        return joinInfoMap.get(joinTable);
    }
}
