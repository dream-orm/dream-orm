package com.moxa.dream.module.mapped;

import com.moxa.dream.util.reflection.factory.ObjectFactory;
import com.moxa.dream.util.reflection.wrapper.ObjectFactoryWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MappedResult {
    private Class<? extends Collection> rowType;
    private Class colType;
    private ObjectFactoryWrapper rowObjectFactoryWrapper;
    private ObjectFactoryWrapper colObjectFactoryWrapper;
    private MappedColumn[] primaryList = new MappedColumn[0];
    private MappedColumn[] mappedColumnList = new MappedColumn[0];
    private Map<String, MappedResult> childResultMappingMap = new HashMap<>();
    private String property;

    public MappedResult(Class<? extends Collection> rowType, Class colType, String property) {
        this.rowType = rowType == null ? Collection.class : rowType;
        this.colType = colType == null ? Object.class : colType;
        this.property = property;
        this.rowObjectFactoryWrapper = ObjectFactoryWrapper.wrapper(this.rowType);
        this.colObjectFactoryWrapper = ObjectFactoryWrapper.wrapper(this.colType);
    }

    public Class<? extends Collection> getRowType() {
        return rowType;
    }

    public Class getColType() {
        return colType;
    }

    public String getProperty() {
        return property;
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

    public ObjectFactory newColObjectFactory() {
        return colObjectFactoryWrapper.newObjectFactory();
    }

    public ObjectFactory newRowObjectFactory() {
        return rowObjectFactoryWrapper.newObjectFactory();
    }

}
