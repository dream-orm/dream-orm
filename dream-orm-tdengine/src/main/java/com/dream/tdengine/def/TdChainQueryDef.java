package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.QueryDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainQueryDef extends AbstractTdChainQuery implements QueryDef<TdChainSelectDef, TdChainFromDef, TdChainWhereDef, TdChainGroupByDef, TdChainHavingDef, TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef> {
    public TdChainQueryDef(QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(new QueryStatement(), queryCreatorFactory, flexMapper);
    }
}
