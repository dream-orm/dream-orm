package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractQueryWrapper;
import com.dream.stream.wrapper.OrderByWrapper;

public class DefaultOrderByWrapper<T> extends AbstractQueryWrapper<T> implements OrderByWrapper<T, DefaultLimitWrapper<T>, DefaultUnionWrapper<T>, DefaultForUpdateWrapper<T>, DefaultQueryWrapper<T>> {

    public DefaultOrderByWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(entityType, statement, creatorFactory);
    }
}
