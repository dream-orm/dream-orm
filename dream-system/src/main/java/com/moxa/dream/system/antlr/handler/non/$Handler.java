package com.moxa.dream.system.antlr.handler.non;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.system.antlr.invoker.$Invoker;
import com.moxa.dream.system.antlr.invoker.NonInvoker;

import java.util.List;

public class $Handler extends AbstractHandler {
    private NonInvoker nonInvoker;

    public $Handler(NonInvoker nonInvoker) {
        this.nonInvoker = nonInvoker;
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof InvokerStatement && SystemInvokerFactory.$.equals(((InvokerStatement) statement).getFunction());
    }

    @Override
    public String handlerAfter(Statement statement, Assist assist, String sql, int life) throws AntlrException {
        $Invoker sqlInvoker = ($Invoker) assist.getInvoker(SystemInvokerFactory.NAMESPACE, SystemInvokerFactory.$);
        List<$Invoker.ParamInfo> paramInfoList = sqlInvoker.getParamInfoList();
        if (paramInfoList != null) {
            int size = paramInfoList.size();
            Object value = paramInfoList.get(size - 1).getParamValue();
            if (nonInvoker.isEmpty(value)) {
                paramInfoList.remove(size - 1);
                return "";
            }
        }
        return sql;
    }
}
