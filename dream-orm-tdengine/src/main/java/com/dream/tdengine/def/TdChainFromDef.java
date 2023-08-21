package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.FromDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainFromDef extends AbstractTdChainQuery implements FromDef<TdChainFromDef, TdChainWhereDef, TdChainGroupByDef, TdChainHavingDef, TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef> {
    public TdChainFromDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    public TdChainIntervalDef partitionBy(String... columns) {
        return new TdChainPartitionByDef(statement(), creatorFactory(), flexMapper).partitionBy(columns);
    }

    public TdChainIntervalDef partitionBy(ColumnDef... columnDefs) {
        return new TdChainPartitionByDef(statement(), creatorFactory(), flexMapper).partitionBy(columnDefs);
    }

    public TdChainSlidingDef interval(String intervalVal) {
        return new TdChainPartitionByDef(statement(), creatorFactory(), flexMapper).interval(intervalVal);
    }

    public TdChainSlidingDef interval(String intervalVal, String intervalOffset) {
        return new TdChainPartitionByDef(statement(), creatorFactory(), flexMapper).interval(intervalVal, intervalOffset);
    }
}
