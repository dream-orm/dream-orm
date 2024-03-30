package com.dream.flex.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.factory.FlexQueryFactory;

public abstract class AbstractQueryDef implements QueryDef {
    private QueryStatement statement;
    private FlexQueryFactory creatorFactory;

    public AbstractQueryDef(QueryStatement statement, FlexQueryFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public QueryStatement statement() {
        return statement;
    }

    @Override
    public FlexQueryFactory creatorFactory() {
        return creatorFactory;
    }
}
