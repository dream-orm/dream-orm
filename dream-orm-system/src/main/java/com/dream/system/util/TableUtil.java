package com.dream.system.util;

import com.dream.system.annotation.Table;
import com.dream.system.annotation.View;
import com.dream.util.common.ObjectUtil;

import java.util.HashSet;
import java.util.Set;

class TableUtil {
    public String getTableName(Class<?> type) {
        return getTableName(type, new HashSet<>());
    }

    public String getTableName(Class<?> type, Set<Class<?>> classSet) {
        if (classSet.contains(type)) {
            return null;
        }
        Table tableAnnotation = type.getDeclaredAnnotation(Table.class);
        if (tableAnnotation != null) {
            String table = tableAnnotation.value();
            if (ObjectUtil.isNull(table)) {
                table = SystemUtil.camelToUnderline(type.getSimpleName());
            }
            return table;
        }
        View viewAnnotation = type.getDeclaredAnnotation(View.class);
        if (viewAnnotation != null) {
            classSet.add(type);
            return getTableName(viewAnnotation.value(), classSet);
        }
        return null;
    }
}
