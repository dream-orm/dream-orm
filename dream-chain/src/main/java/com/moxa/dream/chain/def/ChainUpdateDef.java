package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.UpdateStatement;
import com.moxa.dream.flex.def.UpdateDef;
import com.moxa.dream.flex.factory.UpdateCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainUpdateDef extends AbstractChainUpdate implements UpdateDef<ChainUpdateColumnDef> {
    public ChainUpdateDef(UpdateCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new UpdateStatement(), creatorFactory, flexMapper);
    }
}
