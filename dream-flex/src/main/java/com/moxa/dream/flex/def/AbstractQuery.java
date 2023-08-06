package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.factory.QueryCreatorFactory;

public abstract class AbstractQuery implements Query {
    private QueryStatement statement;
    private QueryCreatorFactory creatorFactory;

    public AbstractQuery(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public QueryStatement statement() {
        return statement;
    }

    @Override
    public QueryCreatorFactory creatorFactory() {
        return creatorFactory;
    }
}
