package com.moxa.dream.mate.util;

import com.moxa.dream.antlr.smt.*;

public class MateUtil {
    public static void appendWhere(WhereStatement whereStatement, Statement tenantStatement) {
        Statement condition = whereStatement.getCondition();
        if (condition instanceof ConditionStatement) {
            OperStatement operStatement = ((ConditionStatement) condition).getOper();
            if (operStatement != null && operStatement instanceof OperStatement.ORStatement) {
                condition = new BraceStatement(condition);
            }
        }
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(condition);
        conditionStatement.setOper(new OperStatement.ANDStatement());
        conditionStatement.setRight(tenantStatement);
        whereStatement.setCondition(conditionStatement);
    }
}
