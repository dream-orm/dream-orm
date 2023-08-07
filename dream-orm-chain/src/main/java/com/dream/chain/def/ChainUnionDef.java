package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.UnionDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainUnionDef extends AbstractChainQuery implements UnionDef<ChainForUpdateDef> {
    public ChainUnionDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }
}
