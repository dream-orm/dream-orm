package com.dream.chain.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteDef;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainDeleteDef extends AbstractChainDelete implements DeleteDef<ChainDeleteTableDef> {
    public ChainDeleteDef(DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new DeleteStatement(), creatorFactory, flexMapper);
    }
}
