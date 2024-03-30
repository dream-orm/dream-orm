package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainSLimitDef extends TdChainLimitDef {
    public TdChainSLimitDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }
}
