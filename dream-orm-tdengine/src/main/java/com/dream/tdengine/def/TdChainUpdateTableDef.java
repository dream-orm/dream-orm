package com.dream.tdengine.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateTableDef;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainUpdateTableDef extends AbstractTdChainUpdateDef implements UpdateTableDef<TdChainUpdateColumnDefDef> {
    public TdChainUpdateTableDef(UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new UpdateStatement(), creatorFactory, flexMapper);
    }
}
