package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.QueryStatement;

public class ForUpdateDef implements Query {
    protected QueryStatement statement;

    protected ForUpdateDef(QueryStatement statement) {
        this.statement = statement;
    }

    @Override
    public QueryStatement getStatement() {
        return statement;
    }
}
