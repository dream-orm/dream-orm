package com.dream.lambda.wrapper;

import com.dream.antlr.smt.QueryStatement;
import com.dream.lambda.factory.QueryCreatorFactory;

public interface QueryWrapper {
    QueryStatement statement();

    QueryCreatorFactory creatorFactory();
}
