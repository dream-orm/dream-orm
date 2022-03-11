package com.moxa.dream.module.mapped;

import com.moxa.dream.module.producer.PropertyInfo;
import com.moxa.dream.module.producer.factory.ObjectFactory;
import com.moxa.dream.module.producer.wrapper.ObjectFactoryWrapper;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MappedResult {
    private Class<? extends Collection> rowType;
    private Class colType;
    private ObjectFactoryWrapper objectFactoryWrapper;
    private MappedColumn[] primaryList = new MappedColumn[0];
    private MappedColumn[] mappedColumnList = new MappedColumn[0];
    private Map<String, MappedResult> childResultMappingMap = new HashMap<>();
    private PropertyInfo propertyInfo;

    public MappedResult(Class<? extends Collection> rowType, Class colType, PropertyInfo propertyInfo) {
        this.rowType = rowType;
        this.colType = colType;
        this.propertyInfo = propertyInfo;
        this.objectFactoryWrapper = ObjectFactoryWrapper.wrapper(colType);
    }

    public Class<? extends Collection> getRowType() {
        return rowType;
    }

    public Class getColType() {
        return colType;
    }

    public PropertyInfo getPropertyInfo() {
        return propertyInfo;
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

    public ObjectFactory newObjectFactory() {
        return objectFactoryWrapper.newObjectFactory();
    }
}
