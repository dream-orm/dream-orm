package com.dream.wrap.wrapper;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.QueryCreatorFactory;

public interface QueryWrapper {
    QueryStatement statement();

    QueryCreatorFactory creatorFactory();
}
