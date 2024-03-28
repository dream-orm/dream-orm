package com.dream.lambda.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.lambda.factory.QueryCreatorFactory;
import com.dream.lambda.wrapper.ConditionWrapper;
import com.dream.lambda.wrapper.HavingWrapper;

import java.util.function.Consumer;

public class DefaultHavingWrapper implements HavingWrapper<DefaultOrderByWrapper, DefaultLimitWrapper, DefaultUnionWrapper, DefaultForUpdateWrapper, DefaultQueryWrapper> {
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
