package com.moxa.dream.system.util;

import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.util.common.ObjectUtil;

public class InvokerUtil {
    public static boolean is$(Statement statement) {
        if (InvokerUtil.isInvoker(statement)) {
            InvokerStatement invokerStatement = (InvokerStatement) statement;
            String namespace = invokerStatement.getNamespace();
            String function = invokerStatement.getFunction();
            return (namespace == null || SystemInvokerFactory.NAMESPACE.equals(namespace))
                    && SystemInvokerFactory.$.equals(function);
        } else
            return false;
    }

    public static boolean isInvoker(Statement statement) {
        if (statement == null)
            return false;
        return statement instanceof InvokerStatement;
    }

    public static InvokerStatement wrapperInvoker(String namespace, String function, String cut, Statement... statementList) {
        InvokerStatement invokerStatement = new InvokerStatement();
        invokerStatement.setNamespace(namespace);
        invokerStatement.setFunction(function);
        ListColumnStatement listColumnStatement = new ListColumnStatement(cut);
        if (!ObjectUtil.isNull(statementList)) {
            for (Statement statement : statementList) {
                listColumnStatement.add(statement);
            }
        }
        invokerStatement.setParamStatement(listColumnStatement);
        return invokerStatement;
    }

    public static String wrapperInvokerSQL(String namespace, String function, String cut, String... paramList) {
        StringBuilder paramBuilder = new StringBuilder();
        if (!ObjectUtil.isNull(paramList)) {
            for (String param : paramList) {
                paramBuilder.append(param).append(cut);
            }
            paramBuilder.delete(paramBuilder.length() - cut.length(), paramBuilder.length());
        }
        return "@" + function + ":" + namespace + "(" + paramBuilder + ")";
    }
}
