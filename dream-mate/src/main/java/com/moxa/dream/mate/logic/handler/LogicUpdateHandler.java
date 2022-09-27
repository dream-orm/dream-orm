package com.moxa.dream.mate.logic.handler;

import com.moxa.dream.antlr.config.Assist;
import com.moxa.dream.antlr.exception.InvokerException;
import com.moxa.dream.antlr.handler.AbstractHandler;
import com.moxa.dream.antlr.invoker.Invoker;
import com.moxa.dream.antlr.smt.*;
import com.moxa.dream.antlr.sql.ToSQL;
import com.moxa.dream.mate.logic.invoker.LogicInvoker;
import com.moxa.dream.mate.util.MateUtil;

import java.util.List;

public class LogicUpdateHandler extends AbstractHandler {
    private LogicInvoker logicInvoker;

    public LogicUpdateHandler(LogicInvoker logicInvoker) {
        this.logicInvoker = logicInvoker;

    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws InvokerException {
        UpdateStatement updateStatement = (UpdateStatement) statement;
        SymbolStatement tableSymbolStatement = (SymbolStatement) updateStatement.getTable();
        String table = tableSymbolStatement.getValue();
        if (logicInvoker.isLogicDelete(table)) {
            ConditionStatement whereConditionStatement = new ConditionStatement();
            whereConditionStatement.setLeft(new SymbolStatement.LetterStatement(logicInvoker.getLogicColumn()));
            whereConditionStatement.setOper(new OperStatement.EQStatement());
            whereConditionStatement.setRight(new SymbolStatement.LetterStatement(logicInvoker.getNegativeValue()));
            WhereStatement whereStatement = (WhereStatement) updateStatement.getWhere();
            if (whereStatement == null) {
                whereStatement = new WhereStatement();
                whereStatement.setCondition(whereConditionStatement);
                updateStatement.setWhere(whereStatement);
            } else {
                MateUtil.appendWhere(whereStatement, whereStatement);
            }
            return updateStatement;
        }
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist sqlAssist) {
        return statement instanceof UpdateStatement;
    }

}
