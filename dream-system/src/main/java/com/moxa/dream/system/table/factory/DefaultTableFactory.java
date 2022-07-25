package com.moxa.dream.system.table.factory;


import com.moxa.dream.system.annotation.Column;
import com.moxa.dream.system.annotation.Id;
import com.moxa.dream.system.annotation.Join;
import com.moxa.dream.system.annotation.Table;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.JoinInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.LowHashMap;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTableFactory implements TableFactory {
    private final Map<String, TableInfo> tableInfoMap = new LowHashMap<>();

    protected TableInfo createTableInfo(Class tableClass) {
        String table = getTable(tableClass);
        if (ObjectUtil.isNull(table))
            return null;
        Map<String, JoinInfo> joinInfoMap = new LowHashMap<>();
        Map<String, String> fieldMap = new LowHashMap<>();
        Map<String, ColumnInfo> columnInfoMap = new HashMap<>();
        List<Field> fieldList = ReflectUtil.findField(tableClass);
        ColumnInfo primaryColumnInfo = null;
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                String name = field.getName();
                ColumnInfo columnInfo = getColumnInfo(table, field);
                if (columnInfo != null) {
                    fieldMap.put(columnInfo.getColumn(), name);
                    columnInfoMap.put(name, columnInfo);
                    if (columnInfo.isPrimary()) {
                        ObjectUtil.requireTrue(primaryColumnInfo == null, tableClass.getName() + " already exist primary Key");
                        primaryColumnInfo = columnInfo;
                    }
                } else {
                    JoinInfo joinInfo = getJoinInfo(table, field);
                    if (joinInfo != null)
                        joinInfoMap.put(joinInfo.getJoinTable(), joinInfo);
                }
            }
        }
        return new TableInfo(table, primaryColumnInfo, columnInfoMap, joinInfoMap, fieldMap);
    }

    protected String getTable(Class tableClass) {
        Table tableAnnotation = (Table) tableClass.getDeclaredAnnotation(Table.class);
        if (tableAnnotation == null)
            return null;
        String table = tableAnnotation.value();
        if (ObjectUtil.isNull(table))
            table = humpToLine(tableClass.getSimpleName());
        return table;
    }

    protected boolean isPrimary(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    protected ColumnInfo getColumnInfo(String table, Field field) {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null)
            return null;
        String column = columnAnnotation.value();
        if (ObjectUtil.isNull(column))
            column = humpToLine(field.getName());
        else
            column = columnAnnotation.value();
        return new ColumnInfo(table, column, field, isPrimary(field), columnAnnotation.jdbcType());
    }

    protected JoinInfo getJoinInfo(String table, Field field) {
        Join joinAnnotation = field.getDeclaredAnnotation(Join.class);
        if (joinAnnotation == null)
            return null;
        Class<?> colType = ReflectUtil.getColType(field.getGenericType());
        String joinTable = getTable(colType);
        ObjectUtil.requireNonNull(joinTable, "Property 'table' is required");
        JoinInfo joinInfo = new JoinInfo(table, joinAnnotation.column(), joinTable, joinAnnotation.joinColumn(), joinAnnotation.joinType());
        return joinInfo;
    }

    protected String humpToLine(String name) {
        StringBuilder builder = new StringBuilder();
        for (char c : name.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                if (builder.length() != 0)
                    builder.append("_");
                builder.append(Character.toLowerCase(c));
            } else
                builder.append(c);
        }
        return builder.toString();
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
