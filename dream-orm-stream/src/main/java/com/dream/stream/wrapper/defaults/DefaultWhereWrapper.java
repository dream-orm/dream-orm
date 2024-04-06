package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractWhereConditionQueryWrapper;
import com.dream.stream.wrapper.WhereWrapper;

public class DefaultWhereWrapper<T> extends AbstractWhereConditionQueryWrapper<T, DefaultWhereWrapper<T>> implements WhereWrapper<T, DefaultGroupByWrapper<T>, DefaultHavingWrapper<T>, DefaultOrderByWrapper<T>, DefaultLimitWrapper<T>, DefaultUnionWrapper<T>, DefaultForUpdateWrapper<T>, DefaultQueryWrapper<T>> {

    public DefaultWhereWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(entityType, statement, creatorFactory);
    }
}
