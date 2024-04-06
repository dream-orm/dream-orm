package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractHavingConditionQueryWrapper;
import com.dream.stream.wrapper.HavingWrapper;

public class DefaultHavingWrapper<T> extends AbstractHavingConditionQueryWrapper<T, DefaultHavingWrapper<T>> implements HavingWrapper<T, DefaultOrderByWrapper<T>, DefaultLimitWrapper<T>, DefaultUnionWrapper<T>, DefaultForUpdateWrapper<T>, DefaultQueryWrapper<T>> {
    public DefaultHavingWrapper(Class<T> entityType, QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(entityType, statement, creatorFactory);
    }
}
