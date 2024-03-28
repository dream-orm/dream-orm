package com.dream.lambda.wrapper.defaults;

import com.dream.antlr.smt.ConditionStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.lambda.factory.QueryCreatorFactory;
import com.dream.lambda.wrapper.ConditionWrapper;
import com.dream.lambda.wrapper.QueryWrapper;

public class DefaultQueryWrapper extends ConditionWrapper<DefaultQueryWrapper> implements QueryWrapper {
    @Override
    public QueryStatement statement() {
        return null;
    }

    @Override
    public QueryCreatorFactory creatorFactory() {
        return null;
    }

    @Override
    protected void accept(ConditionStatement statement) {

    }
}
