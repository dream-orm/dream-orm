package com.dream.stream.wrapper;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;

public class AbstractQueryWrapper implements QueryWrapper {
    private QueryStatement statement;
    private StreamQueryFactory creatorFactory;

    public AbstractQueryWrapper(QueryStatement statement, StreamQueryFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public QueryStatement statement() {
        return statement;
    }

    @Override
    public StreamQueryFactory creatorFactory() {
        return creatorFactory;
    }
}
