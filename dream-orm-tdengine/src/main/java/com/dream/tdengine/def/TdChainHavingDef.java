package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.HavingDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainHavingDef extends AbstractTdChainQuery implements HavingDef<TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef> {
    public TdChainHavingDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }
}
