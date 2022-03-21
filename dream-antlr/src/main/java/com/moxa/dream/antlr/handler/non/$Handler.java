package com.moxa.dream.antlr.handler.non;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.factory.AntlrInvokerFactory;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.$Invoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.antlr.util.ExprUtil;

import java.util.List;

public class $Handler extends AbstractHandler {
    @Override
    protected boolean interest(Statement statement, ToAssist assist) {
        return statement instanceof InvokerStatement && AntlrInvokerFactory.$.equals(((InvokerStatement) statement).getFunction());
    }

    //@?函数翻译之后情形
    @Override
    public String handlerAfter(ToAssist assist, String sql, int life) throws InvokerException {
        //获取@$函数
        $Invoker sqlInvoker = ($Invoker) assist.getInvoker(AntlrInvokerFactory.NAMESPACE, AntlrInvokerFactory.$);
        //获取参数
        List<$Invoker.ParamInfo> paramInfoList = sqlInvoker.getParamInfoList();
        if (paramInfoList != null) {
            int size = paramInfoList.size();
            Object value = paramInfoList.get(size - 1).getParamValue();
            //如果为空则参数去除，ok!
            if (ExprUtil.isEmpty(String.valueOf(value))) {
                paramInfoList.remove(size - 1);
                return null;
            }
        }
        return sql;
    }

    //处理之前，不处理
    @Override
    protected Statement handlerBefore(Statement statement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        return statement;
    }
}
