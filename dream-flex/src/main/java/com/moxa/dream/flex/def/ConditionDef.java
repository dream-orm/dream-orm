package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.BraceStatement;
import com.moxa.dream.antlr.smt.ConditionStatement;
import com.moxa.dream.antlr.smt.OperStatement;
import com.moxa.dream.antlr.smt.Statement;

public class ConditionDef {
    protected ConditionStatement statement;

    protected ConditionDef(ConditionStatement conditionStatement) {
        this.statement = conditionStatement;
    }

    public ConditionDef and(ConditionDef conditionDef) {
        return conditionDef(conditionDef, new OperStatement.ANDStatement());
    }

    public ConditionDef or(ConditionDef conditionDef) {
        return conditionDef(conditionDef, new OperStatement.ORStatement());
    }

    protected ConditionDef conditionDef(ConditionDef conditionDef, OperStatement operStatement) {
        ConditionStatement conditionStatement = new ConditionStatement();
        conditionStatement.setLeft(getWrapStatement(this.statement, operStatement));
        conditionStatement.setOper(operStatement);
        conditionStatement.setRight(getWrapStatement(conditionDef.getStatement(), operStatement));
        this.statement = conditionStatement;
        return this;
    }

    protected Statement getWrapStatement(ConditionStatement conditionStatement, OperStatement operStatement) {
        Statement wrapStatement;
        if (conditionStatement.getOper().getLevel() < operStatement.getLevel()) {
            BraceStatement braceStatement = new BraceStatement(conditionStatement);
            wrapStatement = braceStatement;
        } else {
            wrapStatement = conditionStatement;
        }
        return wrapStatement;
    }

    public ConditionStatement getStatement() {
        return statement;
    }
}
