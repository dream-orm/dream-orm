package com.dream.tdengine.def;

import com.dream.flex.def.SelectDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;
import com.dream.tdengine.statement.TdQueryStatement;

public class TdChainSelectDef extends AbstractTdChainQueryDef implements SelectDef<TdChainFromDef, TdChainWhereDef, TdChainGroupByDef, TdChainHavingDef, TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    public TdChainSelectDef(QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(new TdQueryStatement(), queryCreatorFactory, flexMapper);
    }
}
