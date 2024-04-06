package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractWhereConditionQueryWrapper;
import com.dream.stream.wrapper.FromWrapper;

public class DefaultFromWrapper<T> extends AbstractWhereConditionQueryWrapper<T, DefaultFromWrapper<T>> implements FromWrapper<T, DefaultWhereWrapper<T>, DefaultGroupByWrapper<T>, DefaultHavingWrapper<T>, DefaultOrderByWrapper<T>, DefaultLimitWrapper<T>, DefaultUnionWrapper<T>, DefaultForUpdateWrapper<T>, DefaultQueryWrapper<T>> {

    public DefaultFromWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(entityType, statement, creatorFactory);
    }
}
