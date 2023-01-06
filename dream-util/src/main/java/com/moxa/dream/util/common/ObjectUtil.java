package com.moxa.dream.util.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

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

    public static <T> T[] merge(T[] source1, T[] source2) {
        if (source1 == null) {
            return source2;
        }
        if (source2 == null) {
            return source1;
        }
        Object[] source = new Object[source1.length + source2.length];
        System.arraycopy(source1, 0, source, 0, source1.length);
        System.arraycopy(source2, 0, source, source1.length, source2.length);
        return (T[]) source;
    }

}
