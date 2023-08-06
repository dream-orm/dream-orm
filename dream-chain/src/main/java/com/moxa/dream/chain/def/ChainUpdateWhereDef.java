package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.def.UpdateWhereDef;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainUpdateWhereDef extends AbstractChainUpdate implements UpdateWhereDef {
    public ChainUpdateWhereDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
