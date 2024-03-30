package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.SelectDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainQueryDef extends AbstractTdChainQueryDef implements SelectDef<TdChainFromDef, TdChainWhereDef, TdChainGroupByDef, TdChainHavingDef, TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    public TdChainQueryDef(QueryStatement statement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(statement, flexQueryFactory, flexMapper);
    }
}
