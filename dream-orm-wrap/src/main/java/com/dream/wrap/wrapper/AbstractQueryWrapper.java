package com.dream.wrap.wrapper;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.WrapQueryFactory;

public class AbstractQueryWrapper implements QueryWrapper {
    private QueryStatement statement;
    private WrapQueryFactory creatorFactory;

    public AbstractQueryWrapper(QueryStatement statement, WrapQueryFactory creatorFactory) {
        this.statement = statement;
        this.creatorFactory = creatorFactory;
    }

    @Override
    public QueryStatement statement() {
        return statement;
    }

    @Override
    public WrapQueryFactory creatorFactory() {
        return creatorFactory;
    }
}
