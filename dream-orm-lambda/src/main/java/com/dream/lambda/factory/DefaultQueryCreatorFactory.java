package com.dream.lambda.factory;

import com.dream.antlr.smt.QueryStatement;
import com.dream.lambda.wrapper.defaults.*;

public class DefaultQueryCreatorFactory implements QueryCreatorFactory<DefaultSelectWrapper, DefaultFromWrapper, DefaultWhereWrapper, DefaultGroupByWrapper, DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {

    @Override
    public DefaultSelectWrapper newSelectWrapper() {
        return new DefaultSelectWrapper(this);
    }

    @Override
    public DefaultFromWrapper newFromWrapper(QueryStatement statement) {
        return null;
    }

    @Override
    public DefaultWhereWrapper newWhereWrapper(QueryStatement statement) {
        return null;
    }

    @Override
    public DefaultGroupByWrapper newGroupByWrapper(QueryStatement statement) {
        return null;
    }

    @Override
    public DefaultHavingWrapper newHavingWrapper(QueryStatement statement) {
        return null;
    }

    @Override
    public DefaultOrderByWrapper newOrderByWrapper(QueryStatement statement) {
        return null;
    }

    @Override
    public DefaultLimitWrapper newLimitWrapper(QueryStatement statement) {
        return null;
    }

    @Override
    public DefaultUnionWrapper newUnionWrapper(QueryStatement statement) {
        return null;
    }

    @Override
    public DefaultForUpdateWrapper newForUpdateWrapper(QueryStatement statement) {
        return null;
    }

    @Override
    public DefaultQueryWrapper newQueryWrapper(QueryStatement statement) {
        return null;
    }
}
