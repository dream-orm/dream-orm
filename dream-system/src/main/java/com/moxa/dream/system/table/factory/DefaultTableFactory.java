package com.moxa.dream.system.table.factory;


import com.moxa.dream.system.annotation.Column;
import com.moxa.dream.system.annotation.Join;
import com.moxa.dream.system.annotation.Table;
import com.moxa.dream.system.table.ColumnInfo;
import com.moxa.dream.system.table.JoinInfo;
import com.moxa.dream.system.table.TableInfo;
import com.moxa.dream.util.common.LowHashMap;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.annotation.Annotation;
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
        ColumnInfo primColumnInfo = null;
        if (!ObjectUtil.isNull(fieldList)) {
            for (Field field : fieldList) {
                String name = field.getName();
                ColumnInfo columnInfo = getColumnInfo(table, field);
                if (columnInfo != null) {
                    fieldMap.put(columnInfo.getColumn(), name);
                    columnInfoMap.put(name, columnInfo);
                    if (columnInfo.isPrimary()) {
                        if (primColumnInfo != null) {
                            throw new DreamRunTimeException("类'" + tableClass.getName() + "只能存在一个主键");
                        }
                        primColumnInfo = columnInfo;
                    }
                } else {
                    JoinInfo joinInfo = getJoinInfo(table, field);
                    if (joinInfo != null)
                        joinInfoMap.put(joinInfo.getJoinTable(), joinInfo);
                }
            }
        }
        return new TableInfo(table, primColumnInfo, columnInfoMap, joinInfoMap, fieldMap);
    }

    protected String getTable(Class<?> tableClass) {
        Table tableAnnotation = tableClass.getDeclaredAnnotation(Table.class);
        if (tableAnnotation == null)
            return null;
        String table = tableAnnotation.value();
        return table;
    }

    protected Map<Class<? extends Annotation>, Annotation> annotationMap(Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        Map<Class<? extends Annotation>, Annotation> annotationMap = new HashMap();
        if (!ObjectUtil.isNull(annotations)) {
            for (Annotation annotation : annotations) {
                annotationMap.put(annotation.annotationType(), annotation);
            }
        }
        return annotationMap;
    }

    protected ColumnInfo getColumnInfo(String table, Field field) {
        Column columnAnnotation = field.getDeclaredAnnotation(Column.class);
        if (columnAnnotation == null)
            return null;
        String column = columnAnnotation.value();
        return new ColumnInfo(table, column, field, annotationMap(field), columnAnnotation.jdbcType());
    }

    protected JoinInfo getJoinInfo(String table, Field field) {
        Join joinAnnotation = field.getDeclaredAnnotation(Join.class);
        if (joinAnnotation == null)
            return null;
        Class<?> colType = ReflectUtil.getColType(field.getGenericType());
        String joinTable = getTable(colType);
        ObjectUtil.requireNonNull(joinTable, "@Join修饰的属性'" + field.getName() + "'未获取关联表名");
        JoinInfo joinInfo = new JoinInfo(table, joinAnnotation.column(), joinTable, joinAnnotation.joinColumn(), joinAnnotation.joinType());
        return joinInfo;
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
