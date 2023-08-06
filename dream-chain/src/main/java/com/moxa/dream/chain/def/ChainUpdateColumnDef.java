package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.def.UpdateColumnDef;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainUpdateColumnDef extends AbstractChainUpdate implements UpdateColumnDef<ChainUpdateColumnDef, ChainUpdateWhereDef> {
    public ChainUpdateColumnDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
