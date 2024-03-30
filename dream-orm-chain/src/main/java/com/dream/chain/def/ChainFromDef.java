package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.FromDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainFromDef extends AbstractChainQueryDef implements FromDef<ChainWhereDef, ChainGroupByDef, ChainHavingDef, ChainOrderByDef, ChainLimitDef, ChainUnionDef, ChainForUpdateDef, ChainQueryDef> {
    public ChainFromDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }
}
