package com.moxa.dream.template.util;

import com.moxa.dream.system.annotation.Ignore;
import com.moxa.dream.system.annotation.Table;
import com.moxa.dream.system.annotation.View;
import com.moxa.dream.util.common.LowHashSet;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.exception.DreamRunTimeException;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

class TableUtil {
    public String getTable(Class<?> type) {
        if (type.isAnnotationPresent(View.class)) {
            return type.getDeclaredAnnotation(View.class).value();
        }
        if (type.isAnnotationPresent(Table.class)) {
            return type.getDeclaredAnnotation(Table.class).value();
        }
        return null;
    }

    public Set<String> getTableSet(Class<?> type) {
        Set<String> tableSet = new LowHashSet();
        getTableSet(type, tableSet);
        return tableSet;
    }

    private void getTableSet(Class<?> type, Set<String> tableSet) {
        String table = getTable(type);
        if (!ObjectUtil.isNull(table)) {
            if (!tableSet.contains(table)) {
                tableSet.add(table);
                List<Field> fieldList = ReflectUtil.findField(type);
                if (!ObjectUtil.isNull(fieldList)) {
                    for (Field field : fieldList) {
                        if(!field.isAnnotationPresent(Ignore.class)) {
                            getTableSet(ReflectUtil.getColType(field.getGenericType()), tableSet);
                        }
                    }
                }
            } else {
                throw new DreamRunTimeException("表" + table + "不能重复获取");
            }
        }
    }
}
