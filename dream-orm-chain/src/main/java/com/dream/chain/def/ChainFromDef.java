package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.FromDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainFromDef extends AbstractChainQuery implements FromDef<ChainFromDef, ChainWhereDef> {
    public ChainFromDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }
}
