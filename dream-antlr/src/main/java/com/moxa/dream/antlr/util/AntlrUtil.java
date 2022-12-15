package com.moxa.dream.antlr.util;

import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.smt.SymbolStatement;

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

    public static String getInvokerFunction(Class<? extends Invoker> invokerType) {
        String function;
        String simpleName = invokerType.getSimpleName();
        if (simpleName.endsWith("Invoker") && simpleName.length() > 7) {
            function = simpleName.substring(0, simpleName.length() - 7);
        } else {
            function = simpleName;
        }
        return function.toLowerCase();
    }

    public static InvokerStatement invokerStatement(Invoker invoker, Statement... statements) {
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        if (statements != null) {
            for (Statement statement : statements) {
                listColumnStatement.add(statement);
            }
        }
        InvokerStatement invokerStatement = new InvokerStatement();
        invokerStatement.setFunction(invoker.function());
        invokerStatement.setNamespace(invoker.namespace());
        invokerStatement.setParamStatement(listColumnStatement);
        return invokerStatement;
    }

    public static String invokerSQL(Invoker invoker, String... args) {
        StringBuilder paramBuilder = new StringBuilder();
        if (args != null && args.length > 0) {
            String cut = ",";
            for (String param : args) {
                paramBuilder.append(param).append(",");
            }
            paramBuilder.delete(paramBuilder.length() - cut.length(), paramBuilder.length());
        }
        return "@" + invoker.function() + ":" + invoker.namespace() + "(" + paramBuilder + ")";

    }
}
