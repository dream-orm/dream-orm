package com.dream.wrap.wrapper;

import com.dream.antlr.smt.QueryStatement;
import com.dream.wrap.factory.WrapQueryFactory;

public interface QueryWrapper {
    QueryStatement statement();

    WrapQueryFactory creatorFactory();
}
