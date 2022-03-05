package com.moxa.dream.antlr.util;

import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.util.common.ObjectUtil;

public class InvokerUtil {
    public static boolean is$(Statement statement) {
        if (InvokerUtil.isInvoker(statement)) {
            InvokerStatement invokerStatement = (InvokerStatement) statement;
            String namespace = invokerStatement.getNamespace();
            String function = invokerStatement.getFunction();
            return (namespace == null || AntlrInvokerFactory.NAMESPACE.equals(namespace))
                    && AntlrInvokerFactory.$.equals(function);
        } else
            return false;
    }

    public static boolean isInvoker(Statement statement) {
        if (statement == null)
            return false;
        return statement instanceof InvokerStatement;
    }

    public static InvokerStatement wrapperInvoker(String namespace, String function, String cut, Statement... statementList) {
        ObjectUtil.requireNonNull(namespace, "Property 'namespace' is required");
        ObjectUtil.requireNonNull(function, "Property 'function' is required");
        ObjectUtil.requireNonNull(cut, "Property 'cut' is required");
        InvokerStatement invokerStatement = new InvokerStatement();
        invokerStatement.setNamespace(namespace);
        invokerStatement.setFunction(function);
        ListColumnStatement listColumnStatement = new ListColumnStatement(cut);
        if (!ObjectUtil.isNull(statementList)) {
            for (Statement statement : statementList) {
                ObjectUtil.requireNonNull(statement, "Property 'statement' is required");
                listColumnStatement.add(statement);
            }
        }
        invokerStatement.setListColumnStatement(listColumnStatement);
        return invokerStatement;
    }
}
