package com.dream.mate.logic.handler;

import com.dream.antlr.config.Assist;
import com.dream.antlr.exception.AntlrException;
import com.dream.antlr.expr.OperExpr;
import com.dream.antlr.handler.AbstractHandler;
import com.dream.antlr.invoker.Invoker;
import com.dream.antlr.read.ExprReader;
import com.dream.antlr.smt.*;
import com.dream.antlr.sql.ToSQL;
import com.dream.antlr.util.AntlrUtil;
import com.dream.mate.logic.bean.LogicField;
import com.dream.mate.logic.invoker.LogicInvoker;

import java.util.List;

public class LogicDeleteHandler extends AbstractHandler {
    private final LogicInvoker logicInvoker;

    public LogicDeleteHandler(LogicInvoker logicInvoker) {
        this.logicInvoker = logicInvoker;
    }

    @Override
    protected Statement handlerBefore(Statement statement, Assist assist, ToSQL toSQL, List<Invoker> invokerList, int life) throws AntlrException {
        DeleteStatement deleteStatement = (DeleteStatement) statement;
        SymbolStatement tableSymbolStatement = (SymbolStatement) deleteStatement.getTable();
        String table = tableSymbolStatement.getValue();
        if (logicInvoker.isLogicDelete(assist, table)) {
            ConditionStatement conditionStatement = new ConditionStatement();
            conditionStatement.setLeft(new SymbolStatement.SingleMarkStatement(logicInvoker.getLogicColumn(table)));
            conditionStatement.setOper(new OperStatement.EQStatement());
            conditionStatement.setRight(new SymbolStatement.LetterStatement(logicInvoker.getDeletedValue()));
            ListColumnStatement columnStatements = new ListColumnStatement(",");
            List<LogicField> logicFields = logicInvoker.logicFields(assist, table);
            ConditionStatement[] conditionStatements;
            if (logicFields != null && !logicFields.isEmpty()) {
                conditionStatements = new ConditionStatement[logicFields.size() + 1];
                conditionStatements[0] = conditionStatement;
                for (int i = 0; i < logicFields.size(); i++) {
                    LogicField logicField = logicFields.get(i);
                    Statement valueStatement = new OperExpr(new ExprReader(logicField.getValue()), null).expr();
                    conditionStatements[i + 1] = AntlrUtil.conditionStatement(new SymbolStatement.SingleMarkStatement(logicField.getColumn()), new OperStatement.EQStatement(), valueStatement);
                }
            } else {
                conditionStatements = new ConditionStatement[1];
                conditionStatements[0] = conditionStatement;
            }
            columnStatements.add(conditionStatements);
            UpdateStatement updateStatement = new UpdateStatement();
            updateStatement.setConditionList(columnStatements);
            updateStatement.setTable(tableSymbolStatement);
            updateStatement.setWhere(deleteStatement.getWhere());
            deleteStatement.replaceWith(updateStatement);
            return updateStatement;
        }
        return statement;
    }

    @Override
    protected boolean interest(Statement statement, Assist assist) {
        return statement instanceof DeleteStatement;
    }

}
