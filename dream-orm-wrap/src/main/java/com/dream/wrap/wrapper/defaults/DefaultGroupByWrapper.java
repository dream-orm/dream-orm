package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.QueryCreatorFactory;
import com.dream.wrap.wrapper.AbstractHavingConditionQueryWrapper;
import com.dream.wrap.wrapper.GroupByWrapper;

public class DefaultGroupByWrapper extends AbstractHavingConditionQueryWrapper<DefaultGroupByWrapper> implements GroupByWrapper<DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultGroupByWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
