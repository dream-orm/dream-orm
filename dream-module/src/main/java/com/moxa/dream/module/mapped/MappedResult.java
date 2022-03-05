package com.moxa.dream.module.mapped;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MappedResult {
    private Class rowType;
    private String link;
    private MappedColumn[] primaryList = new MappedColumn[0];
    private MappedColumn[] mappedColumnList = new MappedColumn[0];
    private Map<String, MappedResult> childResultMappingMap = new HashMap<>();

    public MappedResult(Class rowType, String link) {
        this.rowType = rowType;
        this.link = link;
    }

    public Class getRowType() {
        return rowType;
    }

    public String getLink() {
        return link;
    }

    public MappedColumn[] getColumnMappingList() {
        return mappedColumnList;
    }

    public Map<String, MappedResult> getChildResultMappingMap() {
        return childResultMappingMap;
    }

    public MappedColumn[] getPrimaryList() {
        return primaryList;
    }

    public MappedColumn[] getMappedColumnList() {
        return mappedColumnList;
    }

    public boolean isSimple() {
        return childResultMappingMap.isEmpty();
    }

    public void add(MappedColumn mappedColumn) {
        int length = mappedColumnList.length;
        mappedColumnList = Arrays.copyOf(mappedColumnList, length + 1);
        mappedColumnList[length] = mappedColumn;
        if (mappedColumn.isPrimary()) {
            length = primaryList.length;
            primaryList = Arrays.copyOf(primaryList, length + 1);
            primaryList[length] = mappedColumn;
        }
    }
}
