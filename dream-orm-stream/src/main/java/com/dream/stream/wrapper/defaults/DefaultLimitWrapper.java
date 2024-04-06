package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractQueryWrapper;
import com.dream.stream.wrapper.LimitWrapper;

public class DefaultLimitWrapper<T> extends AbstractQueryWrapper<T> implements LimitWrapper<T, DefaultUnionWrapper<T>, DefaultForUpdateWrapper<T>, DefaultQueryWrapper<T>> {

    public DefaultLimitWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(entityType, statement, creatorFactory);
    }
}
