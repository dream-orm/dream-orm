package com.dream.tdengine.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateWhereDef;
import com.dream.flex.factory.FlexUpdateFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainUpdateDef extends AbstractTdChainUpdateDef implements UpdateWhereDef {
    public TdChainUpdateDef(UpdateStatement statement, FlexUpdateFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
