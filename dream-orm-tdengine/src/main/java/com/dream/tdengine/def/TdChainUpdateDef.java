package com.dream.tdengine.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class TdChainUpdateDef extends AbstractTdChainUpdate implements UpdateDef<TdChainUpdateColumnDef> {
    public TdChainUpdateDef(UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new UpdateStatement(), creatorFactory, flexMapper);
    }
}
