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

    public TdChainIntervalDef partitionBy(String... columns) {
        return new TdChainPartitionDef(statement(), creatorFactory(), flexMapper).partitionBy(columns);
    }

    public TdChainIntervalDef partitionBy(ColumnDef... columnDefs) {
        return new TdChainPartitionDef(statement(), creatorFactory(), flexMapper).partitionBy(columnDefs);
    }

    public TdChainSlidingDef interval(String intervalVal) {
        return new TdChainPartitionDef(statement(), creatorFactory(), flexMapper).interval(intervalVal);
    }

    public TdChainSlidingDef interval(String intervalVal, String intervalOffset) {
        return new TdChainPartitionDef(statement(), creatorFactory(), flexMapper).interval(intervalVal, intervalOffset);
    }
}
