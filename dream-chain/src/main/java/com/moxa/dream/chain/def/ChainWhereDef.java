package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.QueryCreatorFactory;
import com.moxa.dream.flex.def.WhereDef;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainWhereDef extends AbstractChainQuery implements WhereDef<ChainGroupByDef> {
    public ChainWhereDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory,flexMapper);
    }
}
