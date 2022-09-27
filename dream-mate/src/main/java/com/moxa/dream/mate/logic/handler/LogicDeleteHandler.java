package com.moxa.dream.mate.logic.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.logic.invoker.LogicInvoker;

import java.util.List;

public class LogicDeleteHandler extends AbstractHandler {
    private LogicInvoker logicInvoker;

    public LogicDeleteHandler(LogicInvoker logicInvoker) {
        this.logicInvoker = logicInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        DeleteStatement deleteStatement = (DeleteStatement) statement;
        SymbolStatement tableSymbolStatement = (SymbolStatement) deleteStatement.getTable();
        String table = tableSymbolStatement.getValue();
        if (logicInvoker.isLogicDelete(table)) {
            ConditionStatement conditionStatement = new ConditionStatement();
            conditionStatement.setLeft(new SymbolStatement.LetterStatement(logicInvoker.getLogicColumn()));
            conditionStatement.setOper(new OperStatement.EQStatement());
            conditionStatement.setRight(new SymbolStatement.LetterStatement(logicInvoker.getPositiveValue()));
            UpdateStatement updateStatement = new UpdateStatement();
            updateStatement.setConditionList(conditionStatement);
            updateStatement.setTable(tableSymbolStatement);
            updateStatement.setWhere(deleteStatement.getWhere());
            deleteStatement.replaceWith(updateStatement);
            return updateStatement;
        }
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof DeleteStatement;
    }

}
