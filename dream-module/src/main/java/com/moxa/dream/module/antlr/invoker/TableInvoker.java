package com.moxa.dream.module.antlr.invoker;

import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.invoker.AbstractInvoker;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.InvokerStatement;
import com.moxa.dream.antlr.smt.ListColumnStatement;
import com.moxa.dream.antlr.smt.Statement;
import com.moxa.dream.antlr.sql.ToAssist;
import com.moxa.dream.antlr.sql.ToSQL;

import java.util.List;

public class TableInvoker extends AbstractInvoker {


    @Override
    public String invoker(InvokerStatement invokerStatement, ToAssist assist, ToSQL toSQL, List<Invoker> invokerList) throws InvokerException {
        Statement[] columnList = ((ListColumnStatement) invokerStatement.getParamStatement()).getColumnList();
        if (columnList.length == 0) {

        } else {

        }
        return null;
    }
}
