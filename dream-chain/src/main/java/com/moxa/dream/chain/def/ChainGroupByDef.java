package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.GroupByDef;
import com.moxa.dream.flex.def.QueryCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainGroupByDef extends AbstractChainQuery implements GroupByDef<ChainHavingDef> {
    public ChainGroupByDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory,flexMapper);
    }
}
