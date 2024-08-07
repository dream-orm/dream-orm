package com.dream.tdengine.def;

import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.FromDef;
import com.dream.flex.def.FunctionDef;
import com.dream.flex.factory.FlexQueryFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainFromDef extends AbstractTdChainQueryDef implements FromDef<TdChainWhereDef, TdChainGroupByDef, TdChainHavingDef, TdChainOrderByDef, TdChainLimitDef, TdChainUnionDef, TdChainForUpdateDef, TdChainQueryDef> {
    public TdChainFromDef(QueryStatement queryStatement, FlexQueryFactory flexQueryFactory, FlexMapper flexMapper) {
        super(queryStatement, flexQueryFactory, flexMapper);
    }

    public TdChainWhereDef from(String table) {
        return from(FunctionDef.tab(table));
    }
}
