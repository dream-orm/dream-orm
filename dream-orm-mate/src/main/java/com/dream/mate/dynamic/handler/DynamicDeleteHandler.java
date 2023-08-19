package com.dream.mate.dynamic.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.DeleteStatement;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.smt.SymbolStatement;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.dynamic.invoker.DynamicGetInvoker;
import com.dream.mate.dynamic.invoker.DynamicInvoker;

import java.util.List;

public class DynamicDeleteHandler extends AbstractHandler {
    private DynamicInvoker dynamicInvoker;

    public DynamicDeleteHandler(DynamicInvoker dynamicInvoker) {
        this.dynamicInvoker = dynamicInvoker;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof DeleteStatement;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        DeleteStatement deleteStatement = (DeleteStatement) statement;
        Statement tableStatement = deleteStatement.getTable();
        if (tableStatement instanceof SymbolStatement) {
            String table = ((SymbolStatement) tableStatement).getValue();
            if (dynamicInvoker.isDynamic(table)) {
                InvokerStatement invokerStatement = AntlrUtil.invokerStatement(
                        DynamicGetInvoker.FUNCTION,
                        Invoker.DEFAULT_NAMESPACE,
                        tableStatement);
                deleteStatement.setTable(invokerStatement);
            }
        }
        return statement;
    }
}
