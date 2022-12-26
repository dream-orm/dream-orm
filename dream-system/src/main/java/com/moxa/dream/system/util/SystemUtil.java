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

    public static String underlineToCamel(String column) {
        if (column == null) {
            return null;
        }
        int len = column.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = Character.toLowerCase(column.charAt(i));
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(column.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
