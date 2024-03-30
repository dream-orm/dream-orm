package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.ForUpdateDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainForUpdateDef extends AbstractTdChainQueryDef implements ForUpdateDef<TdChainQueryDef> {
    public TdChainForUpdateDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }
}
