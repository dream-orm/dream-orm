package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.WrapQueryFactory;
import com.dream.wrap.wrapper.AbstractHavingConditionQueryWrapper;
import com.dream.wrap.wrapper.GroupByWrapper;

public class DefaultGroupByWrapper extends AbstractHavingConditionQueryWrapper<DefaultGroupByWrapper> implements GroupByWrapper<DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultGroupByWrapper(QueryStatement statement, WrapQueryFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
