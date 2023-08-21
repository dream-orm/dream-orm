package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.HavingDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainHavingDef extends AbstractTdChainQueryDef implements HavingDef<TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    public TdChainHavingDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    public TdChainLimitDef sLimit(Integer offset, Integer rows) {
        return new TdChainSUnionDef(statement(), creatorFactory(), flexMapper).sLimit(offset, rows);
    }

    public TdChainLimitDef sOffset(Integer offset, Integer rows) {
        return new TdChainSUnionDef(statement(), creatorFactory(), flexMapper).sOffset(offset, rows);
    }
}
