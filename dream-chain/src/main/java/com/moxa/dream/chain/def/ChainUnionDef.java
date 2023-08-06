package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.UnionDef;
import com.moxa.dream.flex.factory.QueryCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainUnionDef extends AbstractChainQuery implements UnionDef<ChainForUpdateDef> {
    public ChainUnionDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }
}
