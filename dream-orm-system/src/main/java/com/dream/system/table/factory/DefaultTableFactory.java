package com.dream.system.table.factory;


import com.dream.system.annotation.Column;
import com.dream.system.annotation.Table;
import com.dream.system.table.ColumnInfo;
import com.dream.system.table.TableInfo;
import com.dream.system.typehandler.handler.ObjectTypeHandler;
import com.dream.system.typehandler.handler.TypeHandler;
import com.dream.system.util.SystemUtil;
import com.dream.util.common.LowHashMap;
import com.dream.util.common.ObjectUtil;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultTableFactory implements TableFactory {
    private final Map<String, TableInfo> tableInfoMap = new LowHashMap<>();

    protected TableInfo createTableInfo(Class tableClass) {
        String table = getTable(tableClass);
        if (ObjectUtil.isNull(table) || tableInfoMap.get(table) != null) {
            return null;
        }
        Map<String, ColumnInfo> columnInfoMap = new LowHashMap<>(8);
        List<Field> fieldList = ReflectUtil.findField(tableClass);
        List<ColumnInfo> primKeys = new ArrayList<>();
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                String name = field.getName();
                ColumnInfo columnInfo = getColumnInfo(table, field);
                if (columnInfo != null) {
                    columnInfoMap.put(name, columnInfo);
                    columnInfoMap.put(columnInfo.getColumn(), columnInfo);
                    if (columnInfo.isPrimary()) {
                        primKeys.add(columnInfo);
                    }
                }
            }
        }
        return new TableInfo(table, tableClass, primKeys, columnInfoMap);
    }

    protected String getTable(Class<?> tableClass) {
        Table tableAnnotation = tableClass.getDeclaredAnnotation(Table.class);
        if (tableAnnotation == null) {
            return null;
        }
        String table = tableAnnotation.value();
        if (ObjectUtil.isNull(table)) {
            table = SystemUtil.camelToUnderline(tableClass.getSimpleName());
        }
        return table;
    }

    protected ColumnInfo getColumnInfo(String table, Field field) {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null) {
            return null;
        }
        String column = columnAnnotation.value();
        if (ObjectUtil.isNull(column)) {
            column = SystemUtil.camelToUnderline(field.getName());
        }
        Class<? extends TypeHandler> typeHandlerClass = columnAnnotation.typeHandler();
        TypeHandler typeHandler = null;
        if (typeHandlerClass != ObjectTypeHandler.class) {
            typeHandler = ReflectUtil.create(typeHandlerClass);
        }
        return new ColumnInfo(table, column, field, columnAnnotation.jdbcType(), typeHandler);
    }

    @Override
    public void addTableInfo(Class type) {
        TableInfo tableInfo = createTableInfo(type);
        if (tableInfo != null) {
            tableInfoMap.put(tableInfo.getTable(), tableInfo);
        }
    }

    @Override
    public TableInfo getTableInfo(String table) {
        return tableInfoMap.get(table);
    }

}
