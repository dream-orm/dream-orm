package com.dream.flex.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.factory.QueryCreatorFactory;

public interface QueryDef {
    QueryStatement statement();

    QueryCreatorFactory creatorFactory();
}
