package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.OrderByDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainOrderByDef extends AbstractChainQueryDef implements OrderByDef<ChainLimitDef, ChainUnionDef, ChainForUpdateDef, ChainQueryDef> {
    public ChainOrderByDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }
}
