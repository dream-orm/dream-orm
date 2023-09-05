package com.dream.tdengine.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateColumnDef;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainUpdateColumnDef extends AbstractTdChainUpdateDef implements UpdateColumnDef<TdChainUpdateColumnDef, TdChainUpdateDef> {
    public TdChainUpdateColumnDef(UpdateStatement statement, UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
