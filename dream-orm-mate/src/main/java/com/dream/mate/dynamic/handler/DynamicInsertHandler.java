package com.dream.mate.dynamic.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.InsertStatement;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.dynamic.invoker.DynamicGetInvoker;
import com.dream.mate.permission.invoker.PermissionGetInvoker;

import java.util.List;

public class DynamicInsertHandler extends AbstractHandler {

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof InsertStatement;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        InsertStatement insertStatement = (InsertStatement) statement;
        InvokerStatement invokerStatement = AntlrUtil.invokerStatement(
                DynamicGetInvoker.FUNCTION,
                Invoker.DEFAULT_NAMESPACE,
                insertStatement.getTable());
        insertStatement.setTable(invokerStatement);
        return statement;
    }
}
