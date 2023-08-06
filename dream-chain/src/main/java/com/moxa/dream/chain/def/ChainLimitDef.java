package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.LimitDef;
import com.moxa.dream.flex.def.QueryCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainLimitDef extends AbstractChainQuery implements LimitDef<ChainUnionDef> {
    public ChainLimitDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory,FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }
}
