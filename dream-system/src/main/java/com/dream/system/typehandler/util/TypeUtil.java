package com.dream.system.typehandler.util;

import com.dream.util.common.ObjectUtil;
import com.dream.util.reflect.ReflectUtil;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeUtil {
    static Map<Integer, String> typeMap = new HashMap<>(4);

    static {
        List<Field> fieldList = ReflectUtil.findField(Types.class);
        for (int index = 0; index < fieldList.size(); index++) {
            Field field = fieldList.get(index);
            try {
                Object value = field.get(null);
                if (value != null && ReflectUtil.castClass(value.getClass()) == Integer.class) {
                    typeMap.put((Integer) value, field.getName());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getTypeName(int jdbcType) {
        return typeMap.get(jdbcType);
    }

    public static int hash(Class javaType, int jdbcType) {
        return ObjectUtil.hash(javaType, jdbcType);
    }
}
