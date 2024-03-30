package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.QueryCreatorFactory;
import com.dream.wrap.wrapper.AbstractWhereConditionQueryWrapper;
import com.dream.wrap.wrapper.FromWrapper;

public class DefaultFromWrapper extends AbstractWhereConditionQueryWrapper<DefaultFromWrapper> implements FromWrapper<DefaultWhereWrapper, DefaultGroupByWrapper, DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultFromWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
