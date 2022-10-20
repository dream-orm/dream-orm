package com.moxa.dream.system.util;


import com.moxa.dream.system.annotation.Ignore;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class SystemUtil {
    private static TableUtil tableUtil = new TableUtil();

    public static String getTableName(Class<?> type) {
        return tableUtil.getTableName(type);
    }

    public static boolean ignoreField(Field field) {
        int modifier = field.getModifiers();
        if (Modifier.isStatic(modifier)
                || Modifier.isFinal(modifier)
                || field.isAnnotationPresent(Ignore.class)) {
            return true;
        }
        return false;
    }
}
