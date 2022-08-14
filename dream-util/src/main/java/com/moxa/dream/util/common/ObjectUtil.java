package com.moxa.dream.util.common;

import com.moxa.dream.util.exception.DreamRunTimeException;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class ObjectUtil {
    public static void requireNonNull(Object obj, String msg) {
        if (obj == null)
            throw new DreamRunTimeException(msg);
    }

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

}
