package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.DeleteStatement;

public class DeleteWhereDef implements Delete {
    private DeleteStatement statement;

    protected DeleteWhereDef(DeleteStatement statement) {
        this.statement = statement;
    }

    @Override
    public DeleteStatement getStatement() {
        return statement;
    }
}
