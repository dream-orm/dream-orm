package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.WhereDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainWhereDef extends AbstractTdChainQueryDef implements WhereDef<TdChainGroupByDef, TdChainHavingDef, TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    public TdChainWhereDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    @Override
    public TdChainGroupByDef partitionBy(String... columns) {
        return super.partitionBy(columns);
    }

    @Override
    public TdChainGroupByDef partitionBy(ColumnDef... columnDefs) {
        return super.partitionBy(columnDefs);
    }

    @Override
    public TdChainSlidingDef interval(String intervalVal) {
        return super.interval(intervalVal);
    }

    @Override
    public TdChainSlidingDef interval(String intervalVal, String intervalOffset) {
        return super.interval(intervalVal, intervalOffset);
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
