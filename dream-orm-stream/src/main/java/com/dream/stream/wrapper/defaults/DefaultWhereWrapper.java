package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractWhereConditionQueryWrapper;
import com.dream.stream.wrapper.WhereWrapper;

public class DefaultWhereWrapper extends AbstractWhereConditionQueryWrapper<DefaultWhereWrapper> implements WhereWrapper<DefaultGroupByWrapper, DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {

    public DefaultWhereWrapper(QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
