package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.JoinStatement;

public class JoinDef {
    private FromDef fromDef;
    private JoinStatement joinStatement;

    protected JoinDef(FromDef fromDef, JoinStatement joinStatement) {
        this.fromDef = fromDef;
        this.joinStatement = joinStatement;
    }

    public FromDef on(ConditionDef conditionDef) {
        joinStatement.setOn(conditionDef.getStatement());
        return fromDef;
    }

}
