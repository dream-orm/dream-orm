package com.moxa.dream.chain.def;

import com.moxa.dream.antlr.smt.DeleteStatement;
import com.moxa.dream.flex.def.DeleteTableDef;
import com.moxa.dream.flex.factory.DeleteCreatorFactory;
import com.moxa.dream.flex.mapper.FlexMapper;

public class ChainDeleteTableDef extends AbstractChainDelete implements DeleteTableDef<ChainDeleteWhereDef> {
    public ChainDeleteTableDef(DeleteStatement statement, DeleteCreatorFactory creatorFactory, FlexMapper flexMapper) {
        super(statement, creatorFactory, flexMapper);
    }
}
