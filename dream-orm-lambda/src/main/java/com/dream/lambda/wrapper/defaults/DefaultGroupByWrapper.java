package com.dream.lambda.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.lambda.factory.QueryCreatorFactory;
import com.dream.lambda.wrapper.ConditionWrapper;
import com.dream.lambda.wrapper.GroupByWrapper;

import java.util.function.Consumer;

public class DefaultGroupByWrapper implements GroupByWrapper<DefaultHavingWrapper, DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
    @Override
    public QueryStatement statement() {
        return null;
    }

    @Override
    public QueryCreatorFactory creatorFactory() {
        return null;
    }

    @Override
    public DefaultOrderByWrapper having(Consumer<ConditionWrapper> fn) {
        return null;
    }
}
