package com.moxa.dream.system.table;

import java.util.Collection;
import java.util.Map;

public class TableInfo {
    private String table;
    private Map<String, ColumnInfo> columnInfoMap;
    private Map<String, JoinInfo> joinInfoMap;
    private Map<String, String> fieldMap;
    private ColumnInfo columnInfo;

    public TableInfo(String table, ColumnInfo columnInfo, Map<String, ColumnInfo> columnInfoMap, Map<String, JoinInfo> joinInfoMap, Map<String, String> fieldMap) {
        this.table = table;
        this.columnInfo = columnInfo;
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

    public ColumnInfo getColumnInfo() {
        return columnInfo;
    }

    public JoinInfo getJoinInfo(String joinTable) {
        return joinInfoMap.get(joinTable);
    }
}
