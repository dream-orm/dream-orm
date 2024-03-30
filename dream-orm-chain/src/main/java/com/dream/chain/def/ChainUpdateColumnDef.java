package com.dream.chain.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateColumnDef;
import com.dream.flex.factory.FlexUpdateFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainUpdateColumnDef extends AbstractChainUpdateDef implements UpdateColumnDef<ChainUpdateColumnDef, ChainUpdateDef> {
    public ChainUpdateColumnDef(UpdateStatement statement, FlexUpdateFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
