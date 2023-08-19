package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.QueryDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainQueryDef extends AbstractChainQuery implements QueryDef<ChainSelectDef, ChainFromDef, ChainWhereDef, ChainGroupByDef, ChainHavingDef, ChainOrderByDef, ChainLimitDef, ChainUnionDef, ChainForUpdateDef> {
    public ChainQueryDef(QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(new QueryStatement(), queryCreatorFactory, flexMapper);
    }
}
