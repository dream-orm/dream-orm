package com.moxa.dream.system.util;

import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.system.antlr.invoker.$Invoker;

public class InvokerUtil {
    public static boolean is$(Statement statement) {
        if (InvokerUtil.isInvoker(statement)) {
            InvokerStatement invokerStatement = (InvokerStatement) statement;
            String namespace = invokerStatement.getNamespace();
            String function = invokerStatement.getFunction();
            return (namespace == null || Invoker.DEFAULT_NAMESPACE.equals(namespace))
                    && $Invoker.FUNCTION.equals(function);
        } else
            return false;
    }

    public static boolean isInvoker(Statement statement) {
        if (statement == null)
            return false;
        return statement instanceof InvokerStatement;
    }

}
