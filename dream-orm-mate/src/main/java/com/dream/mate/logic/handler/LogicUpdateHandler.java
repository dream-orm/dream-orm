package com.dream.mate.logic.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToSQL;
import com.dream.mate.logic.invoker.LogicInvoker;
import com.dream.mate.util.MateUtil;

import java.util.List;

public class LogicUpdateHandler extends AbstractHandler {
    private LogicInvoker logicInvoker;

    public LogicUpdateHandler(LogicInvoker logicInvoker) {
        this.logicInvoker = logicInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        UpdateStatement updateStatement = (UpdateStatement) statement;
        SymbolStatement tableSymbolStatement = (SymbolStatement) updateStatement.getTable();
        String table = tableSymbolStatement.getValue();
        if (logicInvoker.isLogicDelete(table)) {
            ConditionStatement whereConditionStatement = new ConditionStatement();
            whereConditionStatement.setLeft(new SymbolStatement.LetterStatement(logicInvoker.getLogicColumn()));
            whereConditionStatement.setOper(new OperStatement.NEQStatement());
            whereConditionStatement.setRight(new SymbolStatement.LetterStatement(logicInvoker.getDeletedValue()));
            WhereStatement whereStatement = (WhereStatement) updateStatement.getWhere();
            if (whereStatement == null) {
                whereStatement = new WhereStatement();
                whereStatement.setStatement(whereConditionStatement);
                updateStatement.setWhere(whereStatement);
            } else {
                MateUtil.appendWhere(whereStatement, whereConditionStatement);
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
