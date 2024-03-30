package com.dream.chain.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.factory.FlexUpdateFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainUpdateDef extends AbstractChainUpdateDef implements UpdateDef {
    public ChainUpdateDef(UpdateStatement statement, FlexUpdateFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
