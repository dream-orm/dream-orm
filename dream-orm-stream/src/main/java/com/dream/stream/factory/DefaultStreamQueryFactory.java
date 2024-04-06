package com.dream.stream.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.wrapper.defaults.*;

public class DefaultStreamQueryFactory<T> implements StreamQueryFactory<T, DefaultSelectWrapper<T>, DefaultFromWrapper<T>, DefaultWhereWrapper<T>, DefaultGroupByWrapper<T>, DefaultHavingWrapper<T>, DefaultOrderByWrapper<T>, DefaultLimitWrapper<T>, DefaultUnionWrapper<T>, DefaultForUpdateWrapper<T>, DefaultQueryWrapper<T>> {

    @Override
    public DefaultSelectWrapper<T> newSelectWrapper(Class<T> entityType) {
        return new DefaultSelectWrapper<>(entityType, this);
    }

    @Override
    public DefaultFromWrapper<T> newFromWrapper(Class<T> entityType, QueryStatement statement) {
        return new DefaultFromWrapper<>(entityType, statement, this);
    }

    @Override
    public DefaultWhereWrapper<T> newWhereWrapper(Class<T> entityType, QueryStatement statement) {
        return new DefaultWhereWrapper<>(entityType, statement, this);
    }

    @Override
    public DefaultGroupByWrapper<T> newGroupByWrapper(Class<T> entityType, QueryStatement statement) {
        return new DefaultGroupByWrapper<>(entityType, statement, this);
    }

    @Override
    public DefaultHavingWrapper<T> newHavingWrapper(Class<T> entityType, QueryStatement statement) {
        return new DefaultHavingWrapper<>(entityType, statement, this);
    }

    @Override
    public DefaultOrderByWrapper<T> newOrderByWrapper(Class<T> entityType, QueryStatement statement) {
        return new DefaultOrderByWrapper<>(entityType, statement, this);
    }

    @Override
    public DefaultLimitWrapper<T> newLimitWrapper(Class<T> entityType, QueryStatement statement) {
        return new DefaultLimitWrapper<>(entityType, statement, this);
    }

    @Override
    public DefaultUnionWrapper<T> newUnionWrapper(Class<T> entityType, QueryStatement statement) {
        return new DefaultUnionWrapper<>(entityType, statement, this);
    }

    @Override
    public DefaultForUpdateWrapper<T> newForUpdateWrapper(Class<T> entityType, QueryStatement statement) {
        return new DefaultForUpdateWrapper<>(entityType, statement, this);
    }

    @Override
    public DefaultQueryWrapper<T> newQueryWrapper(Class<T> entityType, QueryStatement statement) {
        return new DefaultQueryWrapper<>(entityType, statement, this);
    }
}
