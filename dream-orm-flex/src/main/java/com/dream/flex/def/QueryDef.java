package com.dream.flex.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.factory.FlexQueryFactory;

public interface QueryDef {
    FlexQueryFactory creatorFactory();

    QueryStatement statement();
}
