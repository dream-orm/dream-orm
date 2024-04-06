package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractHavingConditionQueryWrapper;
import com.dream.stream.wrapper.GroupByWrapper;

public class DefaultGroupByWrapper<T> extends AbstractHavingConditionQueryWrapper<T, DefaultGroupByWrapper<T>> implements GroupByWrapper<T, DefaultHavingWrapper<T>, DefaultOrderByWrapper<T>, DefaultLimitWrapper<T>, DefaultUnionWrapper<T>, DefaultForUpdateWrapper<T>, DefaultQueryWrapper<T>> {

    public DefaultGroupByWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(entityType, statement, creatorFactory);
    }
}
