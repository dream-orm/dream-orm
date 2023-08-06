package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.OrderByDef;
import com.moxa.dream.flex.factory.QueryCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainOrderByDef extends AbstractChainQuery implements OrderByDef<ChainLimitDef> {
    public ChainOrderByDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }
}
