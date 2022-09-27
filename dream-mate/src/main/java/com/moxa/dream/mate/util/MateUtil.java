package com.moxa.dream.mate.util;

import com.moxa.dream.antlr.smt.*;

public class MateUtil {
    public static void appendWhere(WhereStatement whereStatement, Statement tenantStatement) {
        Statement condition = whereStatement.getCondition();
        BraceStatement braceStatement = new BraceStatement(condition);
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(braceStatement);
        conditionStatement.setOper(new OperStatement.ANDStatement());
        conditionStatement.setRight(tenantStatement);
        whereStatement.setCondition(conditionStatement);
    }
}
