package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.LimitDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainLimitDef extends AbstractTdChainQueryDef implements LimitDef<TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    public TdChainLimitDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
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
