package com.dream.wrap.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.QueryCreatorFactory;
import com.dream.wrap.wrapper.AbstractWhereConditionQueryWrapper;
import com.dream.wrap.wrapper.WhereWrapper;

public class DefaultWhereWrapper extends AbstractWhereConditionQueryWrapper<DefaultWhereWrapper> implements WhereWrapper<DefaultGroupByWrapper, DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {

    public DefaultWhereWrapper(QueryStatement statement, QueryCreatorFactory creatorFactory) {
        super(statement, creatorFactory);
    }
}
