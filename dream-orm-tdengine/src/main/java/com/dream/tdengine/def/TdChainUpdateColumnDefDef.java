package com.dream.tdengine.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateDefColumnDef;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainUpdateColumnDefDef extends AbstractTdChainUpdateDef implements UpdateDefColumnDef<TdChainUpdateColumnDefDef, TdChainUpdateDef> {
    public TdChainUpdateColumnDefDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
