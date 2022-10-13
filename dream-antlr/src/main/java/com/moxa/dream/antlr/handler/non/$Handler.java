package com.moxa.dream.antlr.handler.non;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.$Invoker;
import com.moxa.dream.antlr.invoker.NonInvoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;

import java.util.List;

public class $Handler extends AbstractHandler {
    private NonInvoker nonInvoker;

    public $Handler(NonInvoker nonInvoker) {
        this.nonInvoker = nonInvoker;
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof InvokerStatement && AntlrInvokerFactory.$.equals(((InvokerStatement) statement).getFunction());
    }

    @Override
    public String handlerAfter(Statement statement, Assist assist, String sql, int life) {
        $Invoker sqlInvoker = ($Invoker) assist.getInvoker(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$);
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
