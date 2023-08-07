package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.ForUpdateDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainForUpdateDef extends AbstractChainQuery implements ForUpdateDef {

    public ChainForUpdateDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

}
