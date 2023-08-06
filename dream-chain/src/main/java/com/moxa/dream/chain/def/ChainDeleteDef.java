package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.def.DeleteDef;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainDeleteDef extends AbstractChainDelete implements DeleteDef<ChainDeleteTableDef> {
    public ChainDeleteDef(DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(new DeleteStatement(), creatorFactory, flexMapper);
    }
}
