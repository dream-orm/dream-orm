package com.dream.system.antlr.handler.emit;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.EmitStatement;
import com.dream.antlr.smt.InvokerStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.Statement;
import com.dream.antlr.sql.ToSQL;
import com.dream.system.antlr.invoker.MarkInvoker;

import java.util.List;

public class EmitColonHandler extends AbstractHandler {
    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        EmitStatement.ColonStatement colonStatement = (EmitStatement.ColonStatement) statement;
        Statement paramStatement = colonStatement.getStatement();
        InvokerStatement invokerStatement = new InvokerStatement();
        invokerStatement.setNamespace(Invoker.DEFAULT_NAMESPACE);
        invokerStatement.setFunction(MarkInvoker.FUNCTION);
        ListColumnStatement listColumnStatement = new ListColumnStatement(",");
        listColumnStatement.add(paramStatement);
        invokerStatement.setParamStatement(listColumnStatement);
        statement.replaceWith(invokerStatement);
        return invokerStatement;
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof EmitStatement.ColonStatement;
    }
}
