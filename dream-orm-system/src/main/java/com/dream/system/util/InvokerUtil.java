package com.dream.system.util;

import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.Statement;
import com.dream.system.antlr.invoker.MarkInvoker;

public class InvokerUtil {
    public static boolean isMark(Statement statement) {
        if (InvokerUtil.isInvoker(statement)) {
            InvokerStatement invokerStatement = (InvokerStatement) statement;
            String namespace = invokerStatement.getNamespace();
            String function = invokerStatement.getFunction();
            return (namespace == null || Invoker.DEFAULT_NAMESPACE.equals(namespace))
                    && MarkInvoker.FUNCTION.equals(function);
        } else {
            return false;
        }
    }

    public static boolean isInvoker(Statement statement) {
        if (statement == null) {
            return false;
        }
        return statement instanceof InvokerStatement;
    }

}
