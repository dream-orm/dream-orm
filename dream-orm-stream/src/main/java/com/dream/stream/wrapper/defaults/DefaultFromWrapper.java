package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractWhereConditionQueryWrapper;
import com.dream.stream.wrapper.FromWrapper;

public class DefaultFromWrapper extends AbstractWhereConditionQueryWrapper<DefaultFromWrapper> implements FromWrapper<DefaultWhereWrapper, DefaultGroupByWrapper, DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultFromWrapper(QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
