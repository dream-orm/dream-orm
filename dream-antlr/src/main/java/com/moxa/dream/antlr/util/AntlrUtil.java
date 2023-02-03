package com.moxa.dream.antlr.util;

import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;

import java.lang.reflect.Field;
import java.util.Map;

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

    public static InvokerStatement invokerStatement(String function, String namespace, Statement... statements) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        if (statements != null) {
            for (Statement statement : statements) {
                listColumnStatement.add(statement);
            }
        }
        InvokerStatement invokerStatement = new InvokerStatement();
        invokerStatement.setFunction(function);
        invokerStatement.setNamespace(namespace);
        invokerStatement.setParamStatement(listColumnStatement);
        return invokerStatement;
    }

    public static String invokerSQL(String function, String namespace, String... args) {
        StringBuilder paramBuilder = new StringBuilder();
        if (args != null && args.length > 0) {
            String cut = ",";
            for (String param : args) {
                paramBuilder.append(param).append(cut);
            }
            paramBuilder.delete(paramBuilder.length() - cut.length(), paramBuilder.length());
        }
        if (namespace != null && namespace.trim().length() != 0) {
            return "@" + function + ":" + namespace + "(" + paramBuilder + ")";
        } else {
            return "@" + function + "(" + paramBuilder + ")";
        }
    }

    public static String replace(String value, Map<String, String> replaceMap) {
        StringBuilder builder = new StringBuilder();
        int length = value.length();
        int i = 0;
        while (i < length) {
            int point = i;
            String replaceNewStr = null;
            for (int j = i + 1; j <= length; j++) {
                String subStr = value.substring(i, j);
                String newStr = replaceMap.get(subStr);
                if (newStr != null) {
                    point = j;
                    replaceNewStr = newStr;
                }
            }
            if (replaceNewStr != null) {
                i = point;
                builder.append(replaceNewStr);
            } else {
                builder.append(value.charAt(i++));
            }
        }
        return builder.toString();
    }
}
