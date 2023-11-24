package com.dream.system.table;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class TableInfo {
    private final String table;
    private final Class type;
    private final Map<String, ColumnInfo> columnInfoMap;
    private final List<ColumnInfo> primKeys;

    public TableInfo(String table, Class type, List<ColumnInfo> primKeys, Map<String, ColumnInfo> columnInfoMap) {
        this.table = table;
        this.type = type;
        this.primKeys = primKeys;
        this.columnInfoMap = columnInfoMap;
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

    public Class getType() {
        return type;
    }

    public List<ColumnInfo> getPrimKeys() {
        return primKeys;
    }

}
