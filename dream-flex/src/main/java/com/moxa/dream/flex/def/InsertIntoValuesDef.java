package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.InsertStatement;


public class InsertIntoValuesDef implements Insert {
    private InsertStatement statement;

    protected InsertIntoValuesDef(InsertStatement statement) {
        this.statement = statement;
    }

    @Override
    public InsertStatement getStatement() {
        return statement;
    }
}
