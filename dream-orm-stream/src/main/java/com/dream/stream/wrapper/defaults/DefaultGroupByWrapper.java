package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractHavingConditionQueryWrapper;
import com.dream.stream.wrapper.GroupByWrapper;

public class DefaultGroupByWrapper extends AbstractHavingConditionQueryWrapper<DefaultGroupByWrapper> implements GroupByWrapper<DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultGroupByWrapper(QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
