package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.WhereDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainWhereDef extends AbstractTdChainQuery implements WhereDef<TdChainGroupByDef, TdChainHavingDef, TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef> {
    public TdChainWhereDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    public TdChainIntervalDef partitionBy(ColumnDef... columnDefs) {
        return new TdChainPartitionByDef(statement(), creatorFactory(), flexMapper).partitionBy(columnDefs);
    }

    public TdChainHavingDef interval(String intervalVal) {
        return new TdChainPartitionByDef(statement(), creatorFactory(), flexMapper).interval(intervalVal);
    }

    public TdChainHavingDef interval(String intervalVal, String intervalOffset) {
        return new TdChainPartitionByDef(statement(), creatorFactory(), flexMapper).interval(intervalVal, intervalOffset);
    }


}
