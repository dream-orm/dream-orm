package com.dream.system.antlr.invoker;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.invoker.AbstractInvoker;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.util.common.ObjectWrapper;

import java.util.List;

public class RepInvoker extends AbstractInvoker {
    ObjectWrapper paramWrapper;

    @Override
    public Invoker newInstance() {
        return new RepInvoker();
    }

    @Override
    public String function() {
        return "rep";
    }

    @Override
    public String invoker(InvokerStatement invokerStatement, Assist assist, ToSQL toSQL, List<Invoker> invokerList) throws AntlrException {
        if (paramWrapper == null) {
            paramWrapper = assist.getCustom(ObjectWrapper.class);
        }
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        if (columnList.length != 1) {
            throw new AntlrException("函数@" + this.function() + "参数个数错误");
        }
        String paramName = toSQL.toStr(columnList[0], assist, null);
        Object value = paramWrapper.get(paramName);
        if (value == null) {
            return "";
        }
        return value.toString();
    }

}
