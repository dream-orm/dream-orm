package com.dream.lambda.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.lambda.factory.QueryCreatorFactory;
import com.dream.lambda.wrapper.UnionWrapper;

public class DefaultUnionWrapper implements UnionWrapper<DefaultForUpdateWrapper, DefaultQueryWrapper> {
    @Override
    public QueryStatement statement() {
        return null;
    }

    @Override
    public QueryCreatorFactory creatorFactory() {
        return null;
    }
}
