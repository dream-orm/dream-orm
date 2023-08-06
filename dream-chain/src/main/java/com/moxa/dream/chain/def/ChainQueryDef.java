package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.QueryStatement;
import com.moxa.dream.flex.def.QueryDef;
import com.moxa.dream.flex.factory.QueryCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainQueryDef extends AbstractChainQuery implements QueryDef<ChainSelectDef> {
    public ChainQueryDef(QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(new QueryStatement(), queryCreatorFactory, flexMapper);
    }
}
