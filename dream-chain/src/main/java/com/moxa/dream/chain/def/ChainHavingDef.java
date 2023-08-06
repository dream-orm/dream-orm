package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.HavingDef;
import com.moxa.dream.flex.def.QueryCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainHavingDef extends AbstractChainQuery implements HavingDef<ChainOrderByDef> {
    public ChainHavingDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory,flexMapper);
    }
}
