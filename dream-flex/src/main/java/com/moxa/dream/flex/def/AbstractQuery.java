package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.QueryStatement;

public abstract class AbstractQuery implements Query {
    private QueryStatement statement;
    private QueryCreatorFactory queryCreatorFactory;

    public AbstractQuery(QueryStatement statement, QueryCreatorFactory queryCreatorFactory) {
        this.statement = statement;
        this.queryCreatorFactory = queryCreatorFactory;
    }

    @Override
    public QueryStatement statement() {
        return statement;
    }

    @Override
    public QueryCreatorFactory queryCreatorFactory() {
        return queryCreatorFactory;
    }
}
