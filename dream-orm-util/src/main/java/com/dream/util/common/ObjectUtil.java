package com.dream.util.common;

import java.util.*;

public class ObjectUtil {

    public static boolean isNull(Map value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNull(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        char[] valueList = value.toCharArray();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(valueList[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(Collection value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNull(Object[] value) {
        return value == null || value.length == 0;
    }

    protected static boolean isNull(Object value) {
        return value == null;
    }

    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    public static <T> List<T> merge(T[]... sourcesArray) {
        List<T> sourceList = new ArrayList<>();
        if (!isNull(sourcesArray)) {
            for (T[] sources : sourcesArray) {
                if (!isNull(sources)) {
                    sourceList.addAll(Arrays.asList(sources));
                }
            }
        }
        return sourceList;
    }
}
