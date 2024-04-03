package com.dream.stream.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.stream.factory.StreamQueryFactory;
import com.dream.stream.wrapper.AbstractHavingConditionQueryWrapper;
import com.dream.stream.wrapper.HavingWrapper;

public class DefaultHavingWrapper extends AbstractHavingConditionQueryWrapper<DefaultHavingWrapper> implements HavingWrapper<DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultHavingWrapper(QueryStatement statement, StreamQueryFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
