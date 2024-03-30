package com.dream.chain.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.SelectDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainSelectDef extends AbstractChainQueryDef implements SelectDef<ChainFromDef, ChainWhereDef, ChainGroupByDef, ChainHavingDef, ChainOrderByDef, ChainLimitDef, ChainUnionDef, ChainForUpdateDef, ChainQueryDef> {
    public ChainSelectDef(FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(new QueryStatement(), flexQueryFactory, flexMapper);
    }
}
