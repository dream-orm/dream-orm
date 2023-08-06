package com.moxa.dream.flex.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.factory.QueryCreatorFactory;

public interface Query {
    QueryStatement statement();

    QueryCreatorFactory creatorFactory();
}
