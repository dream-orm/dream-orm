package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.ForUpdateDef;
import com.moxa.dream.flex.factory.QueryCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainForUpdateDef extends AbstractChainQuery implements ForUpdateDef {

    public ChainForUpdateDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

}
