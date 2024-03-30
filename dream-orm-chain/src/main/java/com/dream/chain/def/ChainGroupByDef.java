package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.GroupByDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainGroupByDef extends AbstractChainQueryDef implements GroupByDef<ChainHavingDef, ChainOrderByDef, ChainLimitDef, ChainUnionDef, ChainForUpdateDef, ChainQueryDef> {
    public ChainGroupByDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }
}
