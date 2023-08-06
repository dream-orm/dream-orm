package com.dream.flex.def;

import com.dream.antlr.smt.BraceStatement;
import com.dream.antlr.smt.ConditionStatement;
import com.dream.antlr.smt.OperStatement;
import com.dream.antlr.smt.Statement;

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

    public ConditionDef when(boolean effective) {
        if (!effective) {
            this.statement = null;
        }
        return this;
    }

    protected ConditionDef conditionDef(ConditionDef conditionDef, OperStatement operStatement) {
        if (this.statement == null && conditionDef.statement == null) {
            return new ConditionDef(null);
        }
        if (this.statement == null) {
            return conditionDef;
        }
        if (conditionDef.statement == null) {
            return this;
        }
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
