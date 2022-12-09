package com.moxa.dream.antlr.util;

import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.smt.Statement;

import java.lang.reflect.Field;

public class AntlrUtil {
    public static <T extends Statement> void copy(T target, T source) throws AntlrException {
        Field[] fieldList = source.getClass().getDeclaredFields();
        if (fieldList != null && fieldList.length > 0) {
            for (Field field : fieldList) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(source);
                    field.set(target, value);
                } catch (Exception e) {
                    throw new AntlrException("抽象树复制失败" + e.getMessage(), e);
                }
            }
        }
    }
}
