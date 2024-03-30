package com.dream.chain.def;

import com.dream.antlr.smt.UpdateStatement;
import com.dream.flex.def.UpdateTableDef;
import com.dream.flex.factory.FlexUpdateFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainUpdateTableDef extends AbstractChainUpdateDef implements UpdateTableDef<ChainUpdateColumnDef> {
    public ChainUpdateTableDef(FlexUpdateFactory creatorFactory, FlexMapper flexMapper) {
        super(new UpdateStatement(), creatorFactory, flexMapper);
    }
}
