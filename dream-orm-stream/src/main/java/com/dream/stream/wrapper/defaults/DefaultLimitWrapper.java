package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractQueryWrapper;
import com.dream.stream.wrapper.LimitWrapper;

public class DefaultLimitWrapper extends AbstractQueryWrapper implements LimitWrapper<DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultLimitWrapper(QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
