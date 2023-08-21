package com.dream.flex.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.factory.QueryCreatorFactory;

public abstract class AbstractQueryDef implements QueryDef {
    private QueryStatement statement;
    private QueryCreatorFactory creatorFactory;

    public AbstractQueryDef(QueryStatement statement, QueryCreatorFactory creatorFactory) {
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
