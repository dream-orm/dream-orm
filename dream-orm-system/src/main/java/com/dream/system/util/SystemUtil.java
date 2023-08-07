package com.dream.system.util;


import com.dream.system.cache.CacheKey;

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
                || Modifier.isFinal(modifier)) {
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

    public static CacheKey cacheKey(String sql, int split, boolean clean) {
        char[] charList = sql.toCharArray();
        int index;
        if (clean) {
            index = 0;
            for (int i = 0; i < charList.length; i++) {
                char c;
                if (!Character.isWhitespace(c = charList[i])) {
                    charList[index++] = Character.toLowerCase(c);
                }
            }
        } else {
            index = charList.length;
        }
        if (split > index) {
            split = index;
        }
        Object[] updateList = new Object[split + 2];
        updateList[0] = new String(charList, 0, index);
        updateList[1] = index;
        int len = (int) Math.ceil(index / (double) split);
        for (int i = 0; i < split; i++) {
            int sPoint = i * len;
            int size = Math.min((i + 1) * len, index) - sPoint;
            char[] tempChars = new char[size];
            System.arraycopy(charList, sPoint, tempChars, 0, size);
            updateList[i + 2] = new String(tempChars);
        }
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(updateList);
        return cacheKey;
    }
}
