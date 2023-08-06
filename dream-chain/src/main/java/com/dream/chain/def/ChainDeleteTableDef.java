package com.dream.chain.def;

import com.dream.antlr.smt.DeleteStatement;
import com.dream.flex.def.DeleteTableDef;
import com.dream.flex.factory.DeleteCreatorFactory;
import com.dream.flex.mapper.FlexMapper;

public class ChainDeleteTableDef extends AbstractChainDelete implements DeleteTableDef<ChainDeleteWhereDef> {
    public ChainDeleteTableDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
