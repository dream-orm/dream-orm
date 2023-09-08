package com.dream.mate.util;

import com.dream.antlr.smt.*;

public class MateUtil {
    public static void appendWhere(WhereStatement whereStatement, Statement statement) {
        Statement condition = whereStatement.getStatement();
        if (condition instanceof ConditionStatement) {
            OperStatement operStatement = ((ConditionStatement) condition).getOper();
            if (operStatement != null && operStatement instanceof OperStatement.ORStatement) {
                condition = new BraceStatement(condition);
            }
        }
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(condition);
        conditionStatement.setOper(new OperStatement.ANDStatement());
        conditionStatement.setRight(statement);
        whereStatement.setStatement(conditionStatement);
    }
}
