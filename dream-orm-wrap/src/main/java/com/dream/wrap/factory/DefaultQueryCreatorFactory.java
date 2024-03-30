package com.dream.wrap.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.wrapper.defaults.*;

public class DefaultQueryCreatorFactory implements QueryCreatorFactory<DefaultSelectWrapper, DefaultFromWrapper, DefaultWhereWrapper, DefaultGroupByWrapper, DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {

    @Override
    public DefaultSelectWrapper newSelectWrapper(Class<?> entityType) {
        return new DefaultSelectWrapper(entityType, this);
    }

    @Override
    public DefaultFromWrapper newFromWrapper(QueryStatement statement) {
        return new DefaultFromWrapper(statement, this);
    }

    @Override
    public DefaultWhereWrapper newWhereWrapper(QueryStatement statement) {
        return new DefaultWhereWrapper(statement, this);
    }

    @Override
    public DefaultGroupByWrapper newGroupByWrapper(QueryStatement statement) {
        return new DefaultGroupByWrapper(statement, this);
    }

    @Override
    public DefaultHavingWrapper newHavingWrapper(QueryStatement statement) {
        return new DefaultHavingWrapper(statement, this);
    }

    @Override
    public DefaultOrderByWrapper newOrderByWrapper(QueryStatement statement) {
        return new DefaultOrderByWrapper(statement, this);
    }

    @Override
    public DefaultLimitWrapper newLimitWrapper(QueryStatement statement) {
        return new DefaultLimitWrapper(statement, this);
    }

    @Override
    public DefaultUnionWrapper newUnionWrapper(QueryStatement statement) {
        return new DefaultUnionWrapper(statement, this);
    }

    @Override
    public DefaultForUpdateWrapper newForUpdateWrapper(QueryStatement statement) {
        return new DefaultForUpdateWrapper(statement, this);
    }

    @Override
    public DefaultQueryWrapper newQueryWrapper(QueryStatement statement) {
        return new DefaultQueryWrapper(statement, this);
    }
}
