package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.UpdateStatement;

public class UpdateWhereDef implements Update {
    private UpdateStatement statement;

    protected UpdateWhereDef(UpdateStatement statement) {
        this.statement = statement;
    }

    @Override
    public UpdateStatement getStatement() {
        return statement;
    }
}
