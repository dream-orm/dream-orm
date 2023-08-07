package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.LimitDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainLimitDef extends AbstractChainQuery implements LimitDef<ChainUnionDef> {
    public ChainLimitDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }
}
