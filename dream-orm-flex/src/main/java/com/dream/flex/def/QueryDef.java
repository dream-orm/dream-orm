package com.dream.flex.def;

import com.dream.flex.factory.FlexQueryFactory;
import com.dream.struct.command.Query;

public interface QueryDef extends Query {
    FlexQueryFactory creatorFactory();
}
