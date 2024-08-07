package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.def.GroupByDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainGroupByDef extends AbstractTdChainQueryDef implements GroupByDef<TdChainHavingDef, TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    public TdChainGroupByDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }

    public TdChainGroupByDef partitionBy(ColumnDef... columnDefs) {
        return new TdChainPartitionDef(statement(), creatorFactory(), flexMapper).partitionBy(columnDefs);
    }

    public TdChainSlidingDef interval(String intervalVal) {
        return super.interval(intervalVal);
    }

    public TdChainSlidingDef interval(String intervalVal, String intervalOffset) {
        return new TdChainPartitionDef(statement(), creatorFactory(), flexMapper).interval(intervalVal, intervalOffset);
    }


}
