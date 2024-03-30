package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.OrderByDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainOrderByDef extends AbstractTdChainQueryDef implements OrderByDef<TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    public TdChainOrderByDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
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
