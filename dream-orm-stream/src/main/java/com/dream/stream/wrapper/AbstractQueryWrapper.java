package com.dream.stream.wrapper;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;

public class AbstractQueryWrapper<T> implements QueryWrapper<T> {
    private Class<T> entityType;
    private QueryStatement statement;
    private StreamQueryFactory creatorFactory;

    public AbstractQueryWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        this.entityType = entityType;
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public Class<T> entityType() {
        return entityType;
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
