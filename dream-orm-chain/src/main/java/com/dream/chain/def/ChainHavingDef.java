package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.HavingDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainHavingDef extends AbstractChainQueryDef implements HavingDef<ChainOrderByDef, ChainLimitDef, ChainUnionDef, ChainForUpdateDef, ChainQueryDef> {
    public ChainHavingDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }
}
