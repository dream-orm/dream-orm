package com.dream.lambda.wrapper.defaults;

import com.dream.antlr.smt.QueryStatement;
import com.dream.lambda.factory.QueryCreatorFactory;
import com.dream.lambda.wrapper.ForUpdateWrapper;

public class DefaultForUpdateWrapper implements ForUpdateWrapper<DefaultQueryWrapper> {
    @Override
    public QueryStatement statement() {
        return null;
    }

    @Override
    public QueryCreatorFactory creatorFactory() {
        return null;
    }
}
