package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.QueryCreatorFactory;
import com.dream.wrap.wrapper.AbstractHavingConditionQueryWrapper;
import com.dream.wrap.wrapper.HavingWrapper;

public class DefaultHavingWrapper extends AbstractHavingConditionQueryWrapper<DefaultHavingWrapper> implements HavingWrapper<DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    public DefaultHavingWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
