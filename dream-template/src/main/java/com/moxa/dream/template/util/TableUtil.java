package com.moxa.dream.template.util;

import com.moxa.dream.system.annotation.Ignore;
import com.moxa.dream.system.util.SystemUtil;
import com.moxa.dream.util.common.LowHashSet;
import com.moxa.dream.util.common.ObjectUtil;
import com.moxa.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

class TableUtil {
    public Set<String> getTableNameSet(Class<?> type) {
        Set<String> tableNameSet = new LowHashSet();
        getTableNameSet(type, tableNameSet);
        return tableNameSet;
    }

    private void getTableNameSet(Class<?> type, Set<String> tableNameSet) {
        String table = SystemUtil.getTableName(type);
        if (!ObjectUtil.isNull(table)) {
            if (!tableNameSet.contains(table)) {
                tableNameSet.add(table);
                List<Field> fieldList = ReflectUtil.findField(type);
                if (!ObjectUtil.isNull(fieldList)) {
                    for (Field field : fieldList) {
                        if (!SystemUtil.ignoreField(field)) {
                            Ignore ignoreAnnotation = field.getAnnotation(Ignore.class);
                            if (ignoreAnnotation == null || ignoreAnnotation.query()) {
                                getTableNameSet(ReflectUtil.getColType(field.getGenericType()), tableNameSet);
                            }
                        }
                    }
                }
            }
        }
    }
}
