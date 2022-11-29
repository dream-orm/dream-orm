package com.moxa.dream.system.antlr.invoker;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.AntlrException;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.system.antlr.factory.SystemInvokerFactory;
import com.moxa.dream.util.common.ObjectWrapper;

import java.util.List;

public class RepInvoker extends AbstractInvoker {
    ObjectWrapper paramWrapper;

    @Override
    public void init(Assist assist) {
        paramWrapper = assist.getCustom(ObjectWrapper.class);
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        if (columnList.length != 1)
            throw new AntlrException("参数个数错误,不满足@" + SystemInvokerFactory.REP + ":" + SystemInvokerFactory.NAMESPACE + "(value)");
        String paramName = toSQL.toStr(columnList[0], assist, null);
        Object value = paramWrapper.get(paramName);
        if (value == null) {
            return "";
        }
        return value.toString();
    }

}
