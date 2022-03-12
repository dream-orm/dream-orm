package com.moxa.dream.module.hold.table;

import java.util.Collection;
import java.util.Map;

public class TableInfo {
    private String table;
    private Map<String, ColumnInfo> columnInfoMap;
    private Map<String, JoinInfo> joinInfoMap;
    private Map<String, String> fieldMap;
    private ColumnInfo primary;

    public TableInfo(String table, ColumnInfo primary, Map<String, ColumnInfo> columnInfoMap, Map<String, JoinInfo> joinInfoMap, Map<String, String> fieldMap) {
        this.table = table;
        this.primary = primary;
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

    public ColumnInfo getPrimary() {
        return primary;
    }
}
