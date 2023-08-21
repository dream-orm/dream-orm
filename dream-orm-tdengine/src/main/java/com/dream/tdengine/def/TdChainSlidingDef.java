package com.dream.tdengine.def;

import com.dream.antlr.smt.GroupStatement;
import com.dream.antlr.smt.ListColumnStatement;
import com.dream.antlr.smt.QueryStatement;
import com.dream.flex.def.ColumnDef;
import com.dream.flex.factory.QueryCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainSlidingDef extends TdChainFillDef {
    public TdChainSlidingDef(QueryStatement queryStatement, QueryCreatorFactory queryCreatorFactory, FlexMapper flexMapper) {
        super(queryStatement, queryCreatorFactory, flexMapper);
    }

    public TdChainFillDef sliding(String slidingVal){
        return new TdChainFillDef(statement(),creatorFactory(),flexMapper);
    }

}
