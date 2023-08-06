package com.dream.chain.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateDef;
import com.dream.flex.factory.UpdateCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainUpdateDef extends AbstractChainUpdate implements UpdateDef<ChainUpdateColumnDef> {
    public ChainUpdateDef(UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new UpdateStatement(), creatorFactory, flexMapper);
    }
}
