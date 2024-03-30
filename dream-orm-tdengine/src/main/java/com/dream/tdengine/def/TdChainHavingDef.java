package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.HavingDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainHavingDef extends AbstractTdChainQueryDef implements HavingDef<TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    public TdChainHavingDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }

    @Override
    public TdChainLimitDef sLimit(Integer offset, Integer rows) {
        return super.sLimit(offset, rows);
    }

    @Override
    public TdChainLimitDef sOffset(Integer offset, Integer rows) {
        return super.sOffset(offset, rows);
    }
}
