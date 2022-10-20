package com.moxa.dream.system.util;

import com.moxa.dream.system.annotation.Table;
import com.moxa.dream.system.annotation.View;

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
            return tableAnnotation.value();
        }
        View viewAnnotation = type.getDeclaredAnnotation(View.class);
        if (viewAnnotation != null) {
            classSet.add(type);
            return getTableName(viewAnnotation.value(), classSet);
        }
        return null;
    }
}
