package com.dream.lambda.wrapper;

import com.dream.antlr.smt.QueryStatement;
import com.dream.lambda.factory.QueryCreatorFactory;

public class AbstractQueryWrapper implements QueryWrapper {
    private QueryStatement statement;
    private QueryCreatorFactory creatorFactory;

    public AbstractQueryWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
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
