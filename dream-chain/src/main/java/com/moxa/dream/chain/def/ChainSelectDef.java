package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.QueryCreatorFactory;
import com.moxa.dream.flex.def.SelectDef;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainSelectDef extends AbstractChainQuery implements SelectDef<ChainFromDef> {
    public ChainSelectDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory,flexMapper);
    }
}
